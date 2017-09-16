package io.github.yabs.yabsmobile

import android.content.Context
import android.preference.PreferenceManager
import android.support.multidex.MultiDexApplication
import com.elpassion.android.commons.sharedpreferences.createSharedPrefs
import com.elpassion.sharedpreferences.gsonadapter.gsonConverterAdapter

class YabsApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        contextProvider = { applicationContext }
        val walletManager = WalletManager(applicationContext)
        val yabContractService = YabContractService(ContractAdressApi.INSTANCE)
        MainActivity.walletManager = walletManager
        RetailerDetails.walletManager = walletManager
        PromoCodeActivity.walletManager = walletManager
        PromoCodeActivity.yabContractService = yabContractService
        RetailerDetails.claimPointsApi = ClaimPointsApi.INSTANCE
        PromoCodeActivity.claimPromoApi = ClaimPromoCodeApi.INSTANCE
        BuySellOfferActivity.api = OffersApi.INSTANCE
        BuySellOfferActivity.yabContractService = yabContractService
    }
}

var contextProvider: () -> Context = { TODO() }

inline fun <reified T> sharedPrefsProvider() =
        createSharedPrefs({ PreferenceManager.getDefaultSharedPreferences(contextProvider()) }, gsonConverterAdapter<T>(gson = gsonProvider))