package com.yabs.hackzurich.dao

import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils

import java.nio.file.Files

class WalletRepository {

    private static String password = "password"
    private static File directory = Files.createTempDirectory("wallets").toFile()
    private static String readOnlyCredentials = WalletUtils.generateLightNewWalletFile(password, directory)


    static String crateNewWalletFile() {
        return WalletUtils.generateLightNewWalletFile(password, directory)
    }

    static Credentials readCredentials(String walletFile) {
        return WalletUtils.loadCredentials(password, new File(directory, walletFile))
    }

    static Credentials readOnlyCredentials() {
        return WalletUtils.loadCredentials(password, new File(directory, readOnlyCredentials))
    }
}
