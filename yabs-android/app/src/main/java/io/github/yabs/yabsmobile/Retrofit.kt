package io.github.yabs.yabsmobile

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val gsonProvider by lazy { Gson() }

val retrofit by lazy {
    Retrofit.Builder()
            .baseUrl(contextProvider().getString(R.string.base_url))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(
                    GsonConverterFactory.create(gsonProvider))
            .client(OkHttpClient().newBuilder().apply {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }.build())
            .build()
}