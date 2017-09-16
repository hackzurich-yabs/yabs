package com.yabs.hackzurich.service

import com.yabs.hackzurich.dao.RetailerRepository
import com.yabs.hackzurich.model.Retailer
import com.yabs.hackzurich.solidity.SolidityService
import com.yabs.hackzurich.solidity.YabsContract
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256

@Service
@CompileStatic
class PointsService {

    @Autowired
    private SolidityService solidityService

    @Autowired
    private RetailerRepository retailerRepository

    void claim(String userKey, String retailerKey, String receiptId) {
        def retailer = retailerRepository.findById(retailerKey).get()
        YabsContract contract = solidityService.getYabsContract(retailer.walletFileName)
        contract.addPoints(new Address(userKey), new Uint256(BigInteger.valueOf(300L)))
    }
}
