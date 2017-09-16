package com.yabs.hackzurich.controller

import com.yabs.hackzurich.service.PromoCodeService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@CompileStatic
class PromoCodeController {

    @Autowired
    private PromoCodeService promoCodeService

    @RequestMapping(value = '/claimPromoCode', method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    String claimPromoCode(@RequestParam String userKey,
                          @RequestParam String retailerKey,
                          @RequestParam String transactionHash,
                          @RequestParam BigInteger points) {
        return "\"${promoCodeService.getPromoCode(userKey, retailerKey, transactionHash, points)}\""
    }

    @RequestMapping(value = '/getPromoCodes', method = RequestMethod.GET, produces = 'application/json')
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<String> getPromoCodes(@RequestParam String userKey,
                               @RequestParam String retailerKey) {
        return promoCodeService.getPromoCodes(userKey, retailerKey)
    }
}
