package com.yabs.hackzurich.model

import groovy.transform.CompileStatic
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.EmbeddedId
import javax.persistence.MappedSuperclass

@MappedSuperclass
@CompileStatic
@ToString
abstract class Offer {

    @EmbeddedId
    OfferId offerId

    @Column(nullable = false)
    String retailerKey

    @Column(nullable = false)
    BigInteger points

    @Column(nullable = false)
    BigInteger yabsPoints

}
