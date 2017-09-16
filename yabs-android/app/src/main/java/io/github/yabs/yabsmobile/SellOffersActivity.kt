package io.github.yabs.yabsmobile

import android.content.Context
import android.os.Bundle
import io.reactivex.Completable
import kotlinx.android.synthetic.main.offers_list.*
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256
import java.util.*

class SellOffersActivity : BuySellOfferActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        offersListTitle.text = "You are buying"
        dialogTitle.text = "Buy ${retailer.name} points"
    }

    override fun extractOffers(buySellOffers: BuySellOffers) = buySellOffers.sellOffers

    override fun fulFillOffer(uid: Long, userKey: String): Completable {
        return yabContractService.executeRx { acceptSellOffer(Address(userKey), Uint256(uid)) }
                .flatMapCompletable { api.acceptSellOffer(userKey, uid, it.transactionHash) }
    }

    override fun createOffer(offerData: OfferData): Completable {
        val randomGen = Random()
        val uid = Math.abs(randomGen.nextLong())
        return yabContractService.executeRx { createBuyOffer(Uint256(uid), Address(retailer.publicKey), Uint256(offerData.points), Uint256(offerData.yabsPoints)) }
                .flatMapCompletable { api.createBuyOffer(offerData.copy(uid = uid)) }
    }

    companion object {
        fun start(context: Context, retailer: Retailer) {
            context.startActivity(intent(context, retailer, SellOffersActivity::class.java))
        }
    }
}