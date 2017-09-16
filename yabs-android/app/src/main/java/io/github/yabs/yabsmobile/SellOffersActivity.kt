package io.github.yabs.yabsmobile

import android.content.Context
import android.os.Bundle

class SellOffersActivity : BuySellOfferActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposable = api.map { it.sellOffers }
                .subscribe()
    }

    override fun createOffer(points: String, yabsPoints: String) {

    }

    companion object {
        fun start(context: Context, retailer: Retailer) {
            context.startActivity(intent(context, retailer))
        }
    }
}