package com.yabs.hackzurich

import com.yabs.hackzurich.dao.RetailerRepository
import com.yabs.hackzurich.model.Retailer
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@CompileStatic
@Component
class DataStarter implements CommandLineRunner {

    private final static List<Retailer> retailers = [
        new Retailer(publicKey: 'publicKey1', privateKey: 'privateKey1', name: 'retailer1'),
        new Retailer(publicKey: 'publicKey2', privateKey: 'privateKey2', name: 'retailer2')
    ]

    @Autowired
    private RetailerRepository retailerRepository


    @Override
    void run(String... strings) throws Exception {
        retailers.each { retailerRepository.save(it) }
    }

}


