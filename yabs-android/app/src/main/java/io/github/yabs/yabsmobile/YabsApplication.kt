package io.github.yabs.yabsmobile

import android.content.Context
import android.preference.PreferenceManager
import android.support.multidex.MultiDexApplication
import com.elpassion.android.commons.sharedpreferences.createSharedPrefs
import com.elpassion.sharedpreferences.gsonadapter.gsonConverterAdapter
import io.github.yabs.yabsmobile.solidity.YabsContract
import org.web3j.abi.datatypes.Address
import org.web3j.crypto.Credentials

class YabsApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        contextProvider = { applicationContext }
        val walletManager = WalletManager(applicationContext)
        val yabContractService = YabContractService(ContractAdressApi.INSTANCE, walletManager)
        MainActivity.walletManager = walletManager
        MainActivity.yabsService = yabContractService
        RetailerDetails.walletManager = walletManager
        PromoCodeActivity.walletManager = walletManager
        PromoCodeActivity.yabContractService = yabContractService
        RetailerDetails.claimPointsApi = ClaimPointsApi.INSTANCE
        PromoCodeActivity.claimPromoApi = ClaimPromoCodeApi.INSTANCE
        BuySellOfferActivity.api = OffersApi.INSTANCE
        BuySellOfferActivity.yabContractService = yabContractService
        BuySellOfferActivity.walletManager = walletManager
        RetailerDetails.yabContractService = yabContractService
    }
}

var contextProvider: () -> Context = { TODO() }

inline fun <reified T> sharedPrefsProvider() =
        createSharedPrefs({ PreferenceManager.getDefaultSharedPreferences(contextProvider()) }, gsonConverterAdapter<T>(gson = gsonProvider))

fun YabsContract.getYabs(wallet: Credentials) = getBalance(Address(wallet.address), Address("0x0"))