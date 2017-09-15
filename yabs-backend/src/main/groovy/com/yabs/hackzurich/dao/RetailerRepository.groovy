package com.yabs.hackzurich.dao

import com.yabs.hackzurich.model.Retailer
import groovy.transform.CompileStatic
import org.springframework.data.repository.CrudRepository

@CompileStatic
interface RetailerRepository extends CrudRepository<Retailer, String> {
}
