package io.github.yabs.yabsmobile

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface BalancesApi {

    @GET("getBalances/{publicKey}")
    fun balances(@Path("publicKey") publicKey: String): Observable<List<Retailer>>

    companion object {
        val INSTANCE by lazy { retrofit.create(BalancesApi::class.java) }
    }
}