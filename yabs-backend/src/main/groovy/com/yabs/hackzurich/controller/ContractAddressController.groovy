package com.yabs.hackzurich.controller

import com.yabs.hackzurich.solidity.SolidityConfiguration
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@CompileStatic
class ContractAddressController {

    @Autowired
    private SolidityConfiguration configuration

    @GetMapping(value = "/contractAddress")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    String getBalances() {
        return configuration.yabsAddress
    }
}
