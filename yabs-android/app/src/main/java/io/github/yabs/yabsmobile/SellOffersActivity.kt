package io.github.yabs.yabsmobile

import android.content.Context
import android.os.Bundle
import kotlinx.android.synthetic.main.offers_list.*

class SellOffersActivity : BuySellOfferActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        offersListTitle.text = "Sell offers"
        dialogTitle.text = "Sell offer"
    }

    override fun extractOffers(buySellOffers: BuySellOffers) = buySellOffers.sellOffers

    override fun fulFillOffer(offerData: OfferData) = BuySellOfferActivity.api.createSellOffer(offerData)

    companion object {
        fun start(context: Context, retailer: Retailer) {
            context.startActivity(intent(context, retailer, SellOffersActivity::class.java))
        }
    }
}