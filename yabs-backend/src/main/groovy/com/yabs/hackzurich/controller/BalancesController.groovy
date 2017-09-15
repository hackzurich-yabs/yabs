package com.yabs.hackzurich.controller

import com.yabs.hackzurich.dto.BalanceData
import com.yabs.hackzurich.service.BalancesService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@CompileStatic
class BalancesController {

    @Autowired
    private BalancesService balancesService

    @RequestMapping(value = "/getBalances", method = RequestMethod.GET, produces = 'application/json')
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<BalanceData> getBalances(String userPublicKey) {
        return balancesService.getBalancesForUser(userPublicKey)
    }

}
