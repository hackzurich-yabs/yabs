package io.github.yabs.yabsmobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.elpassion.android.commons.recycler.adapters.basicAdapterWithLayoutAndBinder
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler
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
                .subscribe({
                    offersList.adapter = basicAdapterWithLayoutAndBinder(it, R.layout.offer_field) { holder, item ->
                        holder.itemView.buyOfferYabsTextView.text = item.points.toString()
                        holder.itemView.buyOfferYabsTextView.text = item.yabsPoints.toString()
                        holder.itemView.buyOfferRateTextView.text = (item.points.toDouble() / item.yabsPoints.toDouble()).toString()
                        holder.itemView.setOnClickListener {
                            disposable = fulFillOffer(points = item.points, yabsPoints = item.yabsPoints, id = item.uid)
                                    .subscribeOn(IoScheduler())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({
                                        Toast.makeText(this, "Udalo sie!!", Toast.LENGTH_LONG).show()
                                    }, {
                                        Log.e("kasper", "$it")
                                    })
                        }
                    }
                }, {
                    Log.e("kasper", "$it")
                })
    }

    abstract fun fulFillOffer(points: BigInteger, yabsPoints: BigInteger, id: Long): Completable

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