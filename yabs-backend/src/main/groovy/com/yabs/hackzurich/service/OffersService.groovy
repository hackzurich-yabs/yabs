package com.yabs.hackzurich.service

import com.yabs.hackzurich.dao.BuyOfferRepository
import com.yabs.hackzurich.dao.SellOfferRepository
import com.yabs.hackzurich.dto.OfferData
import com.yabs.hackzurich.dto.OffersData
import com.yabs.hackzurich.model.BuyOffer
import com.yabs.hackzurich.model.Offer
import com.yabs.hackzurich.model.OfferId
import com.yabs.hackzurich.model.SellOffer
import com.yabs.hackzurich.solidity.SolidityService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@CompileStatic
class OffersService {

    @Autowired
    private BuyOfferRepository buyOfferRepository

    @Autowired
    private SellOfferRepository sellOfferRepository

    @Autowired
    private SolidityService solidityService

    void createBuyOffer(OfferData offerData) {
        BuyOffer buyOffer = OfferMapper.toBuyOffer(offerData)
        buyOfferRepository.save(buyOffer)
    }

    void createSaleOffer(OfferData offerData) {
        SellOffer sellOffer = OfferMapper.toSellOffer(offerData)
        sellOfferRepository.save(sellOffer)
    }

    void acceptBuyOffer(String userKey, long uid, String transactionHash) {
        BuyOffer offer = buyOfferRepository.findById(new OfferId(userKey: userKey, uid: uid)).get()
        verifyWithBlockchain(transactionHash, offer)
        buyOfferRepository.delete(offer)
    }

    void acceptSellOffer(String userKey, long uid, String transactionHash) {
        SellOffer offer = sellOfferRepository.findById(new OfferId(userKey: userKey, uid: uid)).get()
        verifyWithBlockchain(transactionHash, offer)
        sellOfferRepository.delete(offer)
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

    private void verifyWithBlockchain(String transactionHash, Offer offer) {
        solidityService.checkTransactionOnBlockchain(offer.offerId.userKey, offer.retailerKey, transactionHash, offer.points)
    }
}
