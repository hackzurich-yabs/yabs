package io.github.yabs.yabsmobile

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler

abstract class BuySellOfferActivity : AppCompatActivity() {

    private val retailer by lazy { intent.getSerializableExtra(RETAILER_KEY) as Retailer }
    protected var disposable: Disposable? = null
    protected val api = Companion.api.offers(retailer.publicKey)
            .subscribeOn(IoScheduler())
            .observeOn(AndroidSchedulers.mainThread())

    abstract fun createOffer(points: String, yabsPoints: String)

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }

    companion object {
        lateinit var api: OffersApi

        fun intent(context: Context, retailer: Retailer) = Intent(context, BuySellOfferActivity::class.java)
                .putExtra(RETAILER_KEY, retailer)

        private const val RETAILER_KEY = "retailer"
    }
}