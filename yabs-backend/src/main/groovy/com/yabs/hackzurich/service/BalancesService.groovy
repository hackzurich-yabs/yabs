package com.yabs.hackzurich.service

import com.yabs.hackzurich.dto.BalanceData
import groovy.transform.CompileStatic
import org.springframework.stereotype.Service

@Service
@CompileStatic
class BalancesService {

    List<BalanceData> getBalancesForUser(String userPublicKey) { // TODO stubbed
        return [
            new BalanceData(publicKey: userPublicKey, name: 'retailer1', balance: 100),
            new BalanceData(publicKey: 'publicKey2', name: 'retailer2', balance: 0),
            new BalanceData(publicKey: 'publicKey3', name: 'retailer3', balance: null)
        ]
    }
    
}
