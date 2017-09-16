package io.github.yabs.yabsmobile

import android.content.Context
import android.util.Log
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils
import java.io.File

class WalletManager(private val context: Context) {
    private var walletFileName by sharedPrefsProvider<String>().asProperty("walletFile")

    fun getWallet(): Credentials {
        val credentialsVault = File(context.filesDir, "credentials")
        if (!credentialsVault.exists()) {
            credentialsVault.mkdir()
        }
        if (walletFileName == null) {
            val fullNewWalletFile = WalletUtils.generateLightNewWalletFile("password", credentialsVault)
            walletFileName = fullNewWalletFile
            Log.e("kasper", "walletManager created $fullNewWalletFile")
        }
        return WalletUtils.loadCredentials("password", File(File(context.filesDir, "credentials"), walletFileName))
    }
}