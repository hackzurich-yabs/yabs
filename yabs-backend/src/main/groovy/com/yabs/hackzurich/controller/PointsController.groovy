package com.yabs.hackzurich.controller

import com.yabs.hackzurich.service.PointsService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@CompileStatic
class PointsController {

    @Autowired
    private PointsService pointsService

    @RequestMapping(value = '/claimPoints', method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    void claimPoints(@RequestParam String userKey,
                     @RequestParam String retailerKey,
                     @RequestParam String receiptId) {
        pointsService.claim(userKey, retailerKey, receiptId)
    }
}
