package com.yabs.hackzurich.service

import com.yabs.hackzurich.dao.RetailerRepository
import com.yabs.hackzurich.model.Retailer
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils

import java.nio.file.Files

@Service
@CompileStatic
@Slf4j
class WalletService {

    private final String password = "password"
    private final File retailersDirectory = new File(this.class.classLoader.getResource('retailers').toURI())
    private final File tempDirectory = Files.createTempDirectory("wallets").toFile()
    private final String readOnlyFileName = WalletUtils.generateLightNewWalletFile(password, tempDirectory)
    final Credentials readOnlyCredentials = WalletUtils.loadCredentials(password, new File(tempDirectory, readOnlyFileName))

    @Autowired
    RetailerRepository retailerRepository

    void loadRetailersFromFiles() {
//        log.info(retailersDirectory.listFiles() as String)
        retailersDirectory.listFiles().collect { File file ->
            retailerRepository.save(new Retailer(
                publicKey: readCredentialsFromFile(file).address,
                walletFileName: file.name,
                name: file.name.replace('.json', '')
            ))
        }
    }

    Credentials readCredentials(String walletFile) {
        return WalletUtils.loadCredentials(password, new File(retailersDirectory, walletFile))
    }

    private Credentials readCredentialsFromFile(File walletFile) {
        return WalletUtils.loadCredentials(password, walletFile)
    }
}
