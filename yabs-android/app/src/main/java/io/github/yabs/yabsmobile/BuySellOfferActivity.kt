package io.github.yabs.yabsmobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.elpassion.android.commons.recycler.adapters.basicAdapterWithLayoutAndBinder
import com.elpassion.android.commons.recycler.basic.ViewHolderBinder
import com.elpassion.android.view.hide
import com.elpassion.android.view.show
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.main_retailer_field.*
import kotlinx.android.synthetic.main.offer_field.view.*
import kotlinx.android.synthetic.main.offers_list.*
import java.math.BigInteger

abstract class BuySellOfferActivity : AppCompatActivity() {

    protected val retailer by lazy { intent.getSerializableExtra(RETAILER_KEY) as Retailer }
    protected var disposable: Disposable? = null
    private val api by lazy {
        Companion.api.offers(retailer.publicKey)
                .subscribeOn(IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.offers_list)
        offersList.layoutManager = LinearLayoutManager(this)
        disposable = api.map { extractOffers(it) }
                .bindLoader(progressBar)
                .subscribe({
                    offersList.adapter = basicAdapterWithLayoutAndBinder(it, R.layout.offer_field, this::bindOffer)
                }, {
                    Log.e("kasper", "$it")
                })
        addOfferButton.setOnClickListener {
            addOfferDialog.show()
        }
        dialogNegativeButton.setOnClickListener {
            addOfferDialog.hide()
        }
        dialogPositiveButton.setOnClickListener {
            val points = BigInteger(offerPointsAmount.text.toString())
            val yabs = BigInteger(offerYabsPointsAmount.text.toString())
            disposable = createOffer(offerData = OfferData(userKey = walletManager.getWallet().address, uid = 0L, points = points, yabsPoints = yabs, retailersKey = retailer.publicKey))
                    .subscribeOn(IoScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .bindLoader(progressBar)
                    .subscribe({
                        Toast.makeText(this, "Udalo sie!!", Toast.LENGTH_LONG).show()
                    }, {
                        Log.e("kasper", "$it")
                    })
        }
    }

    private fun bindOffer(holder: ViewHolderBinder<OfferData>, offer: OfferData) = with(holder.itemView) {
        buyOfferYabsTextView.text = offer.points.toString()
        buyOfferYabsTextView.text = offer.yabsPoints.toString()
        buyOfferRateTextView.text = (offer.points.toDouble() / offer.yabsPoints.toDouble()).toString()
        setOnClickListener {
            disposable = fulFillOffer(offer.uid, walletManager.getWallet().address)
                    .subscribeOn(IoScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .bindLoader(progressBar)
                    .subscribe({
                        Toast.makeText(context, "Udalo sie!!", Toast.LENGTH_LONG).show()
                    }, {
                        Log.e("kasper", "$it")
                    })
        }
    }

    abstract fun fulFillOffer(uid: Long, userKey: String): Completable

    abstract fun extractOffers(buySellOffers: BuySellOffers): List<OfferData>

    override fun onDestroy() {
        disposable?.dispose()
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

private fun Completable.bindLoader(progressBar: View): Completable {
    return doOnSubscribe { progressBar.show() }
            .doOnDispose { progressBar.hide() }
            .doOnComplete { progressBar.hide() }
}

fun <T> Observable<T>.bindLoader(progressBar: View): Observable<T> {
    return doOnSubscribe { progressBar.show() }
            .doOnDispose { progressBar.hide() }
            .doOnComplete { progressBar.hide() }
}
