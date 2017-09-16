package io.github.yabs.yabsmobile

import android.content.Context
import android.os.Bundle
import io.reactivex.Completable
import kotlinx.android.synthetic.main.offers_list.*
import java.math.BigInteger

class SellOffersActivity : BuySellOfferActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        offersListTitle.text = "Sell offers"
    }

    override fun extractOffers(buySellOffers: BuySellOffers) = buySellOffers.sellOffers

    override fun fulFillOffer(points: BigInteger, yabsPoints: BigInteger, id: Long): Completable {
        return BuySellOfferActivity.api.createSellOffer(OfferData(walletManager.getWallet().address, id, retailer.publicKey, points, yabsPoints))
    }

    companion object {
        fun start(context: Context, retailer: Retailer) {
            context.startActivity(intent(context, retailer, SellOffersActivity::class.java))
        }
    }
}