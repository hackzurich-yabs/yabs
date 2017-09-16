package com.yabs.hackzurich.service

import com.yabs.hackzurich.dao.BuyOfferRepository
import com.yabs.hackzurich.dao.SellOfferRepository
import com.yabs.hackzurich.dto.OfferData
import com.yabs.hackzurich.dto.OffersData
import com.yabs.hackzurich.model.BuyOffer
import com.yabs.hackzurich.model.Offer
import com.yabs.hackzurich.model.SellOffer
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

@Service
@CompileStatic
@Slf4j
class OffersService {

    private BuyOfferRepository buyOfferRepository
    private SellOfferRepository sellOfferRepository

    void createBuyOffer(OfferData offerData) { // TODO to nie dziala :(
        BuyOffer buyOffer = OfferMapper.toBuyOffer(offerData)
        log.info(buyOffer as String)
        buyOfferRepository.save(buyOffer)
    }

    void createSaleOffer(OfferData offerData) { // TODO jw
        SellOffer sellOffer = OfferMapper.toSellOffer(offerData)
        sellOfferRepository.save(sellOffer)
    }

    OffersData getOffersForRetailer(String retailerKey) {
        return new OffersData(
            buyOffers: getBuyOffersForRetailer(retailerKey),
            sellOffers: getSellOffersForRetailer(retailerKey)
        )
    }

    private List<OfferData> getBuyOffersForRetailer(String retailerKey) {
        return buyOfferRepository.findByRetailerKey(retailerKey).collect { OfferMapper.toOfferData(it as Offer) }
    }

    private List<OfferData> getSellOffersForRetailer(String retailerKey) {
        return sellOfferRepository.findByRetailerKey(retailerKey).collect { OfferMapper.toOfferData(it as Offer) }
    }

}
