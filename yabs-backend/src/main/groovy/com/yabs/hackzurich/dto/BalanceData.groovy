package com.yabs.hackzurich.dto

import groovy.transform.CompileStatic
import groovy.transform.Immutable

@CompileStatic
@Immutable
class BalanceData {
    String publicKey
    String name
    BigInteger balance
    BigInteger promoCodePrice
}
