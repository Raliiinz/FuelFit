package com.example.fuelfit.network.auth

interface TokenRefresher {
    suspend fun refreshToken(oldRefresh: String): String?
}
