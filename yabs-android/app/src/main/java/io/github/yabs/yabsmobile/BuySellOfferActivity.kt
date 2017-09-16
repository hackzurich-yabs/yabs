package io.github.yabs.yabsmobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.elpassion.android.commons.recycler.adapters.basicAdapterWithLayoutAndBinder
import com.elpassion.android.commons.recycler.basic.ViewHolderBinder
import com.elpassion.android.view.hide
import com.elpassion.android.view.show
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.offer_field.view.*
import kotlinx.android.synthetic.main.offers_list.*

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
    }

    private fun bindOffer(holder: ViewHolderBinder<OfferData>, offer: OfferData) = with(holder.itemView) {
        buyOfferYabsTextView.text = offer.points.toString()
        buyOfferYabsTextView.text = offer.yabsPoints.toString()
        buyOfferRateTextView.text = (offer.points.toDouble() / offer.yabsPoints.toDouble()).toString()
        setOnClickListener {
            disposable = fulFillOffer(offerData = OfferData(walletManager.getWallet().address, offer.uid, retailer.publicKey, offer.points, offer.yabsPoints))
                    .subscribeOn(IoScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Toast.makeText(context, "Udalo sie!!", Toast.LENGTH_LONG).show()
                    }, {
                        Log.e("kasper", "$it")
                    })
        }
    }

    abstract fun fulFillOffer(offerData: OfferData): Completable

    abstract fun extractOffers(buySellOffers: BuySellOffers): List<OfferData>

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }

    companion object {
        lateinit var api: OffersApi
        lateinit var walletManager: WalletManager

        fun intent(context: Context, retailer: Retailer, clazz: Class<out BuySellOfferActivity>) = Intent(context, clazz)
                .putExtra(RETAILER_KEY, retailer)

        private const val RETAILER_KEY = "retailer"
    }
}