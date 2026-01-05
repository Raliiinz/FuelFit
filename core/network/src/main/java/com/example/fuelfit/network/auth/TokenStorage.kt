package com.example.fuelfit.network.auth

interface TokenStorage {
    suspend fun getToken(): String?
    suspend fun saveToken(token: String)
    suspend fun clear()
}
