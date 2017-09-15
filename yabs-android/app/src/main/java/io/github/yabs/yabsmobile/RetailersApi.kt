package io.github.yabs.yabsmobile

import io.reactivex.Observable
import retrofit2.http.GET

interface RetailersApi {

    @GET("retailers")
    fun call(): Observable<List<Retailer>>

    companion object {
        val INSTANCE by lazy { retrofit.create(RetailersApi::class.java) }
    }
}