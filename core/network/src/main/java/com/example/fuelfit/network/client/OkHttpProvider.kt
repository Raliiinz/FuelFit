package com.example.fuelfit.network.client

import com.example.fuelfit.network.auth.AuthInterceptor
import com.example.fuelfit.network.auth.TokenStorage
import okhttp3.OkHttpClient

object OkHttpProvider {

    fun create(tokenStorage: TokenStorage): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenStorage))
            .addInterceptor(LoggingInterceptor())
            .build()
}
