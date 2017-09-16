package com.yabs.hackzurich.model

import groovy.transform.CompileStatic
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
@CompileStatic
@ToString
class OfferId implements Serializable {

    @Column(nullable = false)
    String userKey

    @Column(nullable = false)
    Long uid
}