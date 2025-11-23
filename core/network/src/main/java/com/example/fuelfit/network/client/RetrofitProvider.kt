package com.example.fuelfit.network.client

import com.example.fuelfit.network.serializer.KotlinSerializationProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object RetrofitProvider {

    fun create(
        baseUrl: String,
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(KotlinSerializationProvider.converterFactory())
            .client(okHttpClient)
            .build()
}

