package com.yabs.hackzurich.model

import groovy.transform.CompileStatic
import groovy.transform.ToString

import javax.persistence.Entity
import javax.persistence.Table

@CompileStatic
@Entity
@Table(name = 'buyOffers')
@ToString(includeSuper = true)
class BuyOffer extends Offer {
}
