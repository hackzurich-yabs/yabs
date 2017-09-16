package com.yabs.hackzurich.dao

import groovy.transform.CompileStatic
import org.springframework.stereotype.Service
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils

import java.nio.file.Files

@Service
@CompileStatic
class WalletRepository {

    private final String password = "password"
    private final File directory = Files.createTempDirectory("wallets").toFile()
    private final String readOnlyFileName = WalletUtils.generateLightNewWalletFile(password, directory)
    final Credentials readOnlyCredentials =
            WalletUtils.loadCredentials(password, new File(directory, readOnlyFileName))

    String crateNewWalletFile() {
        return WalletUtils.generateLightNewWalletFile(password, directory)
    }

    Credentials readCredentials(String walletFile) {
        return WalletUtils.loadCredentials(password, new File(directory, walletFile))
    }

}
