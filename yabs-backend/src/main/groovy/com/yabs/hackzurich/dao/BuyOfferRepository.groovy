package com.yabs.hackzurich.dao

import com.yabs.hackzurich.model.BuyOffer
import com.yabs.hackzurich.model.OfferId
import groovy.transform.CompileStatic
import org.springframework.data.repository.CrudRepository

@CompileStatic
interface BuyOfferRepository extends CrudRepository<BuyOffer, OfferId> {
}
