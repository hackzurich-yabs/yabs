package io.github.yabs.yabsmobile

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GetOffersApi {

    @GET("getOffers")
    fun offers(@Query("retailerKey") retailerKey: String): Observable<BuySellOffers>

    companion object {
        val INSTANCE by lazy { retrofit.create(GetOffersApi::class.java) }
    }
}