package io.github.yabs.yabsmobile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.elpassion.android.commons.recycler.adapters.basicAdapterWithLayoutAndBinder
import com.elpassion.android.commons.recycler.basic.ViewHolderBinder
import com.elpassion.android.view.hide
import com.elpassion.android.view.show
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.offer_field.view.*
import kotlinx.android.synthetic.main.offers_list.*
import kotlinx.android.synthetic.main.progress.*
import kotlinx.android.synthetic.main.retailer_details.*
import kotlinx.android.synthetic.main.toolbar.*
import java.math.BigInteger

abstract class BuySellOfferActivity : AppCompatActivity() {

    protected val retailer by lazy { intent.getSerializableExtra(RETAILER_KEY) as Retailer }

    protected var disposable: CompositeDisposable = CompositeDisposable()
    private val api by lazy {
        Companion.api.offers(retailer.publicKey)
                .subscribeOn(IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.offers_list)
        setSupportActionBar(toolbar_top)
        customizeForRetailer(retailer.name, retailerBackgroundView)
        offersList.layoutManager = LinearLayoutManager(this)
        disposable.add(api.map { extractOffers(it) }
                .bindLoader(progressBar)
                .subscribe({
                    offersList.adapter = basicAdapterWithLayoutAndBinder(it, R.layout.offer_field, this::bindOffer)
                }, {
                    Log.e("kasper", "$it")
                }))
        addOfferButton.setOnClickListener {
            addOfferDialog.show()
        }
        dialogNegativeButton.setOnClickListener {
            addOfferDialog.hide()
        }
        dialogPositiveButton.setOnClickListener {
            val points = BigInteger(offerPointsAmount.text.toString())
            val yabs = BigInteger(offerYabsPointsAmount.text.toString())
            disposable.add(createOffer(offerData = OfferData(userKey = walletManager.getWallet().address, uid = 0L, points = points, yabsPoints = yabs, retailerKey = retailer.publicKey))
                    .subscribeOn(IoScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        hideKeyboard()
                        addOfferDialog.hide()
                    }
                    .bindLoader(progressBar)
                    .subscribe({
                        Toast.makeText(this, "Udalo sie!!", Toast.LENGTH_LONG).show()
                    }, {
                        Log.e("kasper", "$it")
                    }))
        }
    }

    override fun onResume() {
        super.onResume()
        yabsAmountText.reload()
    }


    private fun Activity.hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    private fun bindOffer(holder: ViewHolderBinder<OfferData>, offer: OfferData) = with(holder.itemView) {
        buyOfferPointsTextView.text = offer.points.toString()
        buyOfferYabsTextView.text = offer.yabsPoints.toString()
        buyOfferRateTextView.text = (offer.points.toDouble() / offer.yabsPoints.toDouble()).toString()
        setOnClickListener { _ ->
            disposable.add(fulFillOffer(offer.uid, offer.userKey).toObservable<Unit>()
                    .flatMap { yabContractService.executeRx { getYabs(it) } }
                    .subscribeOn(IoScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .bindLoader(progressBar)
                    .subscribe({ a ->
                        yabsAmountText.text = "${a.value} yabs"
                        Toast.makeText(context, "Udalo sie!!", Toast.LENGTH_LONG).show()
                    }, {
                        Log.e("kasper", "$it")
                    }))
        }
    }

    abstract fun fulFillOffer(uid: Long, userKey: String): Completable

    abstract fun extractOffers(buySellOffers: BuySellOffers): List<OfferData>

    override fun onDestroy() {
        disposable.clear()
        yabsAmountText.stopLoading()
        super.onDestroy()
    }

    companion object {
        lateinit var api: OffersApi
        lateinit var walletManager: WalletManager
        lateinit var yabContractService: YabContractService

        fun intent(context: Context, retailer: Retailer, clazz: Class<out BuySellOfferActivity>) = Intent(context, clazz)
                .putExtra(RETAILER_KEY, retailer)

        private const val RETAILER_KEY = "retailer"
    }

    abstract fun createOffer(offerData: OfferData): Completable
}