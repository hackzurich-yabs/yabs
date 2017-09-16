package com.yabs.hackzurich.model

import groovy.transform.CompileStatic

import javax.persistence.Entity
import javax.persistence.Table

@CompileStatic
@Entity
@Table(name = 'sellOffers')
class SellOffer extends Offer {
}
