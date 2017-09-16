package io.github.yabs.yabsmobile

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import java.math.BigInteger

interface ClaimPromoCodeApi {

    @GET("claimPromoCode")
    fun claim(@Query("userKey") userKey: String,
              @Query("retailerKey") retailerKey: String,
              @Query("transactionHash") transactionHas: String,
              @Query("points") points: BigInteger): Observable<String>

    companion object {
        val INSTANCE by lazy { retrofit.create(ClaimPromoCodeApi::class.java) }
    }
}