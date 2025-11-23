package com.example.fuelfit.auth.impl.data.storage

import com.example.fuelfit.auth.impl.data.remote.AuthApiService
import com.example.fuelfit.auth.impl.data.remote.dto.TokenRefreshRequest
import com.example.fuelfit.network.auth.TokenRefresher

class TokenRefresherImpl(
    private val api: AuthApiService
) : TokenRefresher {

    override suspend fun refreshToken(oldRefresh: String): String? {
        return try {
            val result = api.refresh(TokenRefreshRequest(refresh = oldRefresh))
            result.access
        } catch (e: Exception) {
            null
        }
    }
}
