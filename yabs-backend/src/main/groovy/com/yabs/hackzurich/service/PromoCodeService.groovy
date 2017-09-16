package com.yabs.hackzurich.service

import com.yabs.hackzurich.dao.PromoCodeRepository
import com.yabs.hackzurich.model.PromoCode
import com.yabs.hackzurich.solidity.SolidityService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@CompileStatic
class PromoCodeService {

    private final int promoCodeLength = 6

    @Autowired
    private PromoCodeRepository repository

    @Autowired
    private SolidityService solidityService

    String getPromoCode(String userKey, String retailerKey, String transactionHash, BigInteger points) {
        String code = UUID.randomUUID().toString().substring(0, promoCodeLength)
        solidityService.checkTransactionOnBlockchain(userKey, retailerKey, transactionHash, points)
        repository.save(new PromoCode(userKey: userKey, retailerKey: retailerKey, code: code))
        return code
    }

    List<String> getPromoCodes(String userKey, String retailerKey) {
        return repository.findByUserKeyAndRetailerKey(userKey, retailerKey).collect { PromoCode it -> it.code }
    }
}
