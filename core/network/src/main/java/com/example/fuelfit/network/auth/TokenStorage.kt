package com.example.fuelfit.network.auth

interface TokenStorage {
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun saveAccessToken(token: String)
    suspend fun saveRefreshToken(token: String)
    suspend fun saveTokens(access: String, refresh: String)
    suspend fun clear()
}
