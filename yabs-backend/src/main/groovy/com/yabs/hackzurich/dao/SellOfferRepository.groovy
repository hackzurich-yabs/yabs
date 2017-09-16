package com.yabs.hackzurich.dao

import com.yabs.hackzurich.model.OfferId
import com.yabs.hackzurich.model.SellOffer
import groovy.transform.CompileStatic
import org.springframework.data.repository.CrudRepository

@CompileStatic
interface SellOfferRepository extends CrudRepository<SellOffer, OfferId> {
    Iterable<SellOffer> findByRetailerKey(String retailerKey)
}