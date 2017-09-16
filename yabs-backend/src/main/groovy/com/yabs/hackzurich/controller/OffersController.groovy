package com.yabs.hackzurich.controller

import com.yabs.hackzurich.dto.OfferData
import com.yabs.hackzurich.dto.OffersData
import com.yabs.hackzurich.service.OffersService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@CompileStatic
@Slf4j
class OffersController {

    @Autowired
    private OffersService offersService

    @RequestMapping(value = '/createBuyOffer', method = RequestMethod.POST, consumes = 'application/json')
    @ResponseStatus(HttpStatus.OK)
    void createBuyOffer(@RequestBody OfferData offer) {
        offersService.createBuyOffer(offer)
    }

    @RequestMapping(value = '/createSellOffer', method = RequestMethod.POST, consumes = 'application/json')
    @ResponseStatus(HttpStatus.OK)
    void createSaleOffer(@RequestBody OfferData offer) {
        offersService.createSaleOffer(offer)
    }

    @RequestMapping(value = '/acceptBuyOffer', method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    void acceptBuyOffer(@RequestParam String userKey,
                        @RequestParam Long uid,
                        @RequestParam String transactionHash) {
        offersService.acceptBuyOffer(userKey, uid, transactionHash)
    }

    @RequestMapping(value = '/acceptSellOffer', method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    void acceptSellOffer(@RequestParam String userKey,
                        @RequestParam Long uid,
                        @RequestParam String transactionHash) {
        offersService.acceptSellOffer(userKey, uid, transactionHash)
    }

    @RequestMapping(value = '/getOffers', method = RequestMethod.GET, produces = 'application/json')
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    OffersData getOffers(@RequestParam String retailerKey) {
        offersService.getOffersForRetailer(retailerKey)
    }
}
