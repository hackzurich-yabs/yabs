package io.github.yabs.yabsmobile

import android.content.Context
import android.os.Bundle
import io.reactivex.Completable
import kotlinx.android.synthetic.main.offers_list.*

class BuyOffersActivity : BuySellOfferActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        offersListTitle.text = "Buy offers"
        dialogTitle.text = "Buy offer"
    }

    override fun extractOffers(buySellOffers: BuySellOffers) = buySellOffers.buyOffers

    override fun fulFillOffer(uid: Long, userKey: String) = BuySellOfferActivity.api.acceptSellOffer(userKey, uid, "txHash")

    override fun createOffer(offerData: OfferData): Completable {
        return BuySellOfferActivity.api.createBuyOffer(offerData)
    }

    companion object {
        fun start(context: Context, retailer: Retailer) {
            context.startActivity(intent(context, retailer, BuyOffersActivity::class.java))
        }
    }
}