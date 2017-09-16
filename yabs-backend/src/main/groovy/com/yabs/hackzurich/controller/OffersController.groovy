package com.yabs.hackzurich.controller

import com.yabs.hackzurich.dto.OfferData
import com.yabs.hackzurich.service.OffersService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@CompileStatic
@Slf4j
class OffersController {

    @Autowired
    private OffersService offersService

    @RequestMapping(value = 'createBuyOffer', method = RequestMethod.POST, consumes = 'application/json')
    @ResponseStatus(HttpStatus.OK)
    void createBuyOffer(@RequestBody OfferData offer) {
//        log.info(offer as String)
        offersService.createBuyOffer(offer)
    }

//    @RequestMapping(value = 'getOffers', method = RequestMethod.GET, produces = 'application/json')
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    List<>
}
