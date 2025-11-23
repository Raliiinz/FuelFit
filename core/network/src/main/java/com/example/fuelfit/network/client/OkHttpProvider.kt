package com.example.fuelfit.network.client

import com.example.fuelfit.network.auth.TokenAuthenticator
import com.example.fuelfit.network.auth.TokenInterceptor
import com.example.fuelfit.network.auth.TokenRefresher
import com.example.fuelfit.network.auth.TokenStorage
import okhttp3.OkHttpClient

object OkHttpProvider {

    fun create(
        tokenStorage: TokenStorage,
        tokenRefresher: TokenRefresher
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(tokenStorage))
            .authenticator(TokenAuthenticator(tokenStorage, tokenRefresher))
            .addInterceptor(LoggingInterceptor())
            .build()
}
