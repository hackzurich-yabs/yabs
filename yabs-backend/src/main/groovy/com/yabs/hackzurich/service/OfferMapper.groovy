package com.yabs.hackzurich.service

import com.yabs.hackzurich.dto.OfferData
import com.yabs.hackzurich.model.BuyOffer
import com.yabs.hackzurich.model.Offer
import com.yabs.hackzurich.model.OfferId
import com.yabs.hackzurich.model.SellOffer
import groovy.transform.CompileStatic

@CompileStatic
class OfferMapper {

    static OfferData toOfferData(Offer offer) {
        return new OfferData(
            userKey: offer.offerId.userKey,
            uid: offer.offerId.uid,
            retailerKey: offer.retailerKey,
            points: offer.points,
            yabsPoints: offer.yabsPoints
        )
    }

    static SellOffer toSellOffer(OfferData offerData) {
        new SellOffer(
            offerId: new OfferId(userKey: offerData.userKey, uid: offerData.uid),
            retailerKey: offerData.retailerKey,
            points: offerData.points,
            yabsPoints: offerData.yabsPoints
        )
    }

    static BuyOffer toBuyOffer(OfferData offerData) {
        new BuyOffer(
            offerId: new OfferId(userKey: offerData.userKey, uid: offerData.uid),
            retailerKey: offerData.retailerKey,
            points: offerData.points,
            yabsPoints: offerData.yabsPoints
        )
    }


}
