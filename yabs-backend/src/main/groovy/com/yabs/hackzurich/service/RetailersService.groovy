package com.yabs.hackzurich.service

import com.yabs.hackzurich.dao.RetailerRepository
import com.yabs.hackzurich.dto.RetailerData
import com.yabs.hackzurich.model.Retailer
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@CompileStatic
class RetailersService {

    @Autowired
    private RetailerRepository repository

    List<RetailerData> list() {
        return repository.findAll().collect { Retailer retailer ->
            new RetailerData(name: retailer.name, publicKey: retailer.publicKey)
        }
    }

}
