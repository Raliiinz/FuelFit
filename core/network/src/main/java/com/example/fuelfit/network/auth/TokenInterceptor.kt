package com.example.fuelfit.network.auth

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val tokenStorage: TokenStorage
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val access = runBlocking { tokenStorage.getAccessToken() }
        val newReq = access?.let {
            original.newBuilder()
                .addHeader("Authorization", "Token $it")
                .build()
        } ?: original

        return chain.proceed(newReq)
    }
}
