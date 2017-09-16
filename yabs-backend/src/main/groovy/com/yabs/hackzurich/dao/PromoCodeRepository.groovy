package com.yabs.hackzurich.dao

import com.yabs.hackzurich.model.PromoCode
import groovy.transform.CompileStatic
import org.springframework.data.repository.CrudRepository

@CompileStatic
interface PromoCodeRepository extends CrudRepository<PromoCode, Long> {
    Iterable<PromoCode> findByUserKeyAndRetailerKey(String userKey, String retailerKey)
}