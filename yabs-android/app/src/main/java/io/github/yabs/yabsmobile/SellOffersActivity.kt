package io.github.yabs.yabsmobile

import android.content.Context
import android.os.Bundle
import io.reactivex.Completable
import kotlinx.android.synthetic.main.offers_list.*
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256

class SellOffersActivity : BuySellOfferActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        offersListTitle.text = "Sell offers"
        dialogTitle.text = "Sell offer"
    }

    override fun extractOffers(buySellOffers: BuySellOffers) = buySellOffers.sellOffers

    override fun fulFillOffer(uid: Long, userKey: String): Completable {
        return yabContractService.executeRx { acceptBuyOffer(Address(userKey), Uint256(uid)) }
                .flatMapCompletable { BuySellOfferActivity.api.acceptBuyOffer(userKey, uid, it.transactionHash) }
    }

    override fun createOffer(offerData: OfferData): Completable {
        return BuySellOfferActivity.api.createSellOffer(offerData)
    }

    companion object {
        fun start(context: Context, retailer: Retailer) {
            context.startActivity(intent(context, retailer, SellOffersActivity::class.java))
        }
    }
}