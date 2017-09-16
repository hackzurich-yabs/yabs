package com.yabs.hackzurich.dto

import groovy.transform.CompileStatic
import groovy.transform.Immutable
import groovy.transform.ToString

@Immutable
@ToString
@CompileStatic
class OfferData {
    String userKey
    Long uid
    String retailerKey
    BigInteger points
    BigInteger yabsPoints
}
