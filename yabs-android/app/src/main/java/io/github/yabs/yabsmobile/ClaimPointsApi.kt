package io.github.yabs.yabsmobile

import io.reactivex.Completable
import retrofit2.http.GET
import retrofit2.http.Query

interface ClaimPointsApi {

    @GET("claimPoints")
    fun claim(@Query("userKey") userKey: String,
              @Query("retailerKey") retailerKey: String,
              @Query("receiptId") receiptId: String) : Completable

    companion object {
        val INSTANCE by lazy {
            retrofit.create(ClaimPointsApi::class.java)
        }
    }
}