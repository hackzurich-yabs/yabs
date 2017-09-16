package com.yabs.hackzurich

import com.yabs.hackzurich.dao.RetailerRepository
import com.yabs.hackzurich.dao.WalletRepository
import com.yabs.hackzurich.model.Retailer
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletUtils

import java.nio.file.Files
import java.nio.file.Path

@CompileStatic
@Component
class DataStarter implements CommandLineRunner {

    @Autowired
    private RetailerRepository retailerRepository
    @Autowired
    private WalletRepository walletRepository

    @Override
    void run(String... strings) throws Exception {
        (0..2).each {
            String walletFile = walletRepository.crateNewWalletFile()
            retailerRepository.save(new Retailer(
                    publicKey: walletRepository.readCredentials(walletFile).address,
                    privateKey: walletFile,
                    name: "Zalando"
            ))
        }
    }

}
