package com.example.fuelfit.network.auth

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val tokenStorage: TokenStorage,
    private val tokenRefresher: TokenRefresher
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code != 401) return null

        val refresh = runBlocking { tokenStorage.getRefreshToken() } ?: return null

        val newAccess = runBlocking { tokenRefresher.refreshToken(refresh) }
            ?: return null

        runBlocking { tokenStorage.saveAccessToken(newAccess) }

        return response.request.newBuilder()
            .header("Authorization", "Bearer $newAccess")
            .build()
    }
}
