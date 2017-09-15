package com.yabs.hackzurich.service

import com.yabs.hackzurich.dao.RetailerRepository
import com.yabs.hackzurich.dto.RetailerDto
import com.yabs.hackzurich.model.Retailer
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@CompileStatic
class RetailersService {

    @Autowired
    private RetailerRepository repository

    Iterable<RetailerDto> list() {
        return repository.findAll().collect { Retailer retailer ->
            new RetailerDto(name: retailer.name, publicKey: retailer.publicKey)
        }
    }

}
