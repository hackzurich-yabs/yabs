package io.github.yabs.yabsmobile

import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OffersApi {

    @GET("getOffers")
    fun offers(@Query("retailerKey") retailerKey: String): Observable<BuySellOffers>

    @POST("createSellOffer")
    fun createSellOffer(@Body offer: OfferData) : Completable

    @POST("createBuyOffer")
    fun createBuyOffer(@Body offer: OfferData) : Completable


    @GET("acceptBuyOffer")
    fun acceptBuyOffer(@Query("userKey") userKey: String,
                       @Query("uid") uid: Long,
                       @Query("transactionHash") transactionHash: String) : Completable

    @GET("acceptSellOffer")
    fun acceptSellOffer(@Query("userKey") userKey: String,
                       @Query("uid") uid: Long,
                       @Query("transactionHash") transactionHash: String) : Completable
    companion object {
        val INSTANCE by lazy { retrofit.create(OffersApi::class.java) }
    }
}