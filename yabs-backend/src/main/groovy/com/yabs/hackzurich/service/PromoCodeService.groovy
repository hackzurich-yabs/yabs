package com.yabs.hackzurich.service

import groovy.transform.CompileStatic
import org.springframework.stereotype.Service

@Service
@CompileStatic
class PromoCodeService {

    private final int promoCodeLength = 6

    String getPromoCode() {
        return UUID.randomUUID().toString().substring(0, promoCodeLength)
    }
}
