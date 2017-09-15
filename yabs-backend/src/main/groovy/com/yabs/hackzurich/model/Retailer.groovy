package com.yabs.hackzurich.model

import groovy.transform.CompileStatic

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = 'retailers')
@CompileStatic
class Retailer {

    @Id
    String publicKey

    @Column(nullable = false)
    String privateKey

    @Column(nullable = false)
    String name
}
