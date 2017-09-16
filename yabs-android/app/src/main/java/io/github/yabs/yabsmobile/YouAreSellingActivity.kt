package io.github.yabs.yabsmobile

import android.content.Context
import android.os.Bundle
import io.reactivex.Completable
import kotlinx.android.synthetic.main.offers_list.*
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256
import java.util.*

class YouAreSellingActivity : BuySellOfferActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        offersListTitle.text = "You are selling"
        dialogTitle.text = "Sell ${retailer.name} points"
    }

    override fun extractOffers(buySellOffers: BuySellOffers) = buySellOffers.buyOffers

    override fun fulFillOffer(uid: Long, userKey: String): Completable {
        return yabContractService.executeRx { acceptBuyOffer(Address(userKey), Uint256(uid)) }
                .flatMapCompletable {
                    BuySellOfferActivity.api.acceptBuyOffer(userKey, uid, it.transactionHash)
                }
    }

    override fun createOffer(offerData: OfferData): Completable {
        val randomGen = Random()
        val uid = Math.abs(randomGen.nextLong())
        return yabContractService.executeRx { createSellOffer(Uint256(uid), Address(retailer.publicKey), Uint256(offerData.points), Uint256(offerData.yabsPoints)) }
                .flatMapCompletable { api.createSellOffer(offerData.copy(uid = uid)) }
    }

    override fun startOtherOne() {
        YouAreBuyingActivity.start(this, retailer)
    }

    companion object {
        fun start(context: Context, retailer: Retailer) {
            context.startActivity(intent(context, retailer, YouAreSellingActivity::class.java))
        }
    }
}