package com.yabs.hackzurich.dao

import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils

import java.nio.file.Files

class WalletRepository {

    private static final String password = "password"
    private static final File directory = Files.createTempDirectory("wallets").toFile()
    private static final String readOnlyFileName = WalletUtils.generateLightNewWalletFile(password, directory)
    static final Credentials readOnlyCredentials =
            WalletUtils.loadCredentials(password, new File(directory, readOnlyFileName))

    static String crateNewWalletFile() {
        return WalletUtils.generateLightNewWalletFile(password, directory)
    }

    static Credentials readCredentials(String walletFile) {
        return WalletUtils.loadCredentials(password, new File(directory, walletFile))
    }


}
