package com.yabs.hackzurich.service

import com.yabs.hackzurich.dao.BuyOfferRepository
import com.yabs.hackzurich.dto.OfferData
import com.yabs.hackzurich.model.BuyOffer
import com.yabs.hackzurich.model.OfferId
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

@Service
@CompileStatic
@Slf4j
class OffersService {

    private BuyOfferRepository buyOfferRepository

    void createBuyOffer(OfferData offerData) { // TODO to nie dziala :(
        BuyOffer buyOffer = new BuyOffer(
            offerId: new OfferId(userKey: offerData.userKey, uid: offerData.uid),
            retailerKey: offerData.retailerKey,
            points: offerData.points,
            yabsPoints: offerData.yabsPoints
        )
        log.info(buyOffer as String)
        buyOfferRepository.save(buyOffer)
    }
}
