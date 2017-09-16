package com.yabs.hackzurich.service

import com.yabs.hackzurich.dto.BalanceData
import com.yabs.hackzurich.solidity.SolidityService
import com.yabs.hackzurich.solidity.YabsContract
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.web3j.abi.datatypes.Address

@Service
@CompileStatic
class BalancesService {

    @Autowired
    private RetailersService retailersService

    @Autowired
    private SolidityService solidityService

    List<BalanceData> getBalancesForUser(String userPublicKey) {
        retailersService.list().collect { retailer ->
            new BalanceData(
                publicKey: retailer.publicKey,
                name: retailer.name,
                balance: getBalanceValue(userPublicKey, retailer.publicKey)
            )
        }
    }

    private BigInteger getBalanceValue(String userKey, String retailerKey) {
        YabsContract contract = solidityService.getYabsContract()
        return contract.getBalance(new Address(userKey), new Address(retailerKey)).get().value
    }
}
