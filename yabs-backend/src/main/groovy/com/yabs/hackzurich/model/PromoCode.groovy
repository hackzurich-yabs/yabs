package com.yabs.hackzurich.model

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = 'promo_codes')
@CompileStatic
class PromoCode {

    @GeneratedValue
    @Id
    Long id

    @Column(nullable = false)
    String userKey

    @Column(nullable = false)
    String retailerKey

    @Column(nullable = false)
    String code

}
