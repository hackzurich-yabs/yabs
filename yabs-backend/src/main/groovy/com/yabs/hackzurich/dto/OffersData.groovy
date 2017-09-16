package com.yabs.hackzurich.dto

import groovy.transform.CompileStatic
import groovy.transform.ToString
import jdk.nashorn.internal.ir.annotations.Immutable

@CompileStatic
@Immutable
@ToString
class OffersData {
    List<OfferData> buyOffers
    List<OfferData> sellOffers
}
