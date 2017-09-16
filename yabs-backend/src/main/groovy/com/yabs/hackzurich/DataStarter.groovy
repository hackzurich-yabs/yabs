package com.yabs.hackzurich

import com.yabs.hackzurich.dao.RetailerRepository
import com.yabs.hackzurich.service.WalletService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@CompileStatic
@Component
class DataStarter implements CommandLineRunner {

    @Autowired
    private RetailerRepository retailerRepository
    @Autowired
    private WalletService walletRepository

    @Override
    void run(String... strings) throws Exception {
        walletRepository.loadRetailersFromFiles()
    }

}
