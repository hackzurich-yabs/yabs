package com.yabs.hackzurich.service;

import com.yabs.hackzurich.dto.BalanceData;
import com.yabs.hackzurich.solidity.SolidityUtils;
import com.yabs.hackzurich.solidity.YabsContract;
import groovy.transform.CompileStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Address;

@Service
@CompileStatic
class BalancesService {

    private final RetailersService retailersService;

    @Autowired
    BalancesService(RetailersService retailersService) {
        this.retailersService = retailersService;
    }

    List<BalanceData> getBalancesForUser(final String userPublicKey) {

        final YabsContract contract = SolidityUtils.getYabsContract();

        retailersService.list()
                .collect {
            new BalanceData(
                    it.publicKey,
                    it.name,
                    new BigInteger(contract.getBalance(new Address(userPublicKey), new Address(it.publicKey)).get().toString())
            )
        }
    }
}
