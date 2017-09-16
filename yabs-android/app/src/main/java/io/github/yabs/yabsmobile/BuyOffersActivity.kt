package io.github.yabs.yabsmobile

import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.offers_list.*

class BuyOffersActivity : BuySellOfferActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        offersListTitle.text = "Buy offers"
    }

    override fun extractOffers(buySellOffers: BuySellOffers) = buySellOffers.buyOffers

    override fun fulFillOffer(offerData: OfferData) = BuySellOfferActivity.api.createBuyOffer(offerData)

    companion object {
        fun start(context: Context, retailer: Retailer) {
            context.startActivity(intent(context, retailer, BuyOffersActivity::class.java))
        }
    }
}