package io.github.yabs.yabsmobile

import io.reactivex.Observable
import retrofit2.http.GET

interface ContractAdressApi {

    @GET("contractAddress")
    fun address(): Observable<String>

    companion object {
        val INSTANCE by lazy { retrofit.create(ContractAdressApi::class.java) }
    }
}