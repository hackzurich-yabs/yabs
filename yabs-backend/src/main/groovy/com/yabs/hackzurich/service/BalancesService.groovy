package com.yabs.hackzurich.service;

import com.yabs.hackzurich.dto.BalanceData
import com.yabs.hackzurich.solidity.SolidityService;
import com.yabs.hackzurich.solidity.YabsContract;
import groovy.transform.CompileStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Address;

@Service
@CompileStatic
class BalancesService {

    private final RetailersService retailersService
    private final SolidityService solidityService

    @Autowired
    BalancesService(RetailersService retailersService, SolidityService solidityService) {
        this.retailersService = retailersService
        this.solidityService = solidityService
    }

    List<BalanceData> getBalancesForUser(final String userPublicKey) {

        final YabsContract contract = solidityService.getYabsContract()

        retailersService.list()
                .collect {
            new BalanceData(
                    it.publicKey,
                    it.name,
                    contract.getBalance(new Address(userPublicKey), new Address(it.publicKey)).get().value
            )
        }
    }
}
