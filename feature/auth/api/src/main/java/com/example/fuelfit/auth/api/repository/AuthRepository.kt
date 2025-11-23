package com.example.fuelfit.auth.api.repository

import com.example.fuelfit.auth.api.model.AuthTokens
import com.example.fuelfit.auth.api.model.LoginParams
import com.example.fuelfit.auth.api.model.RegisterParams

interface AuthRepository {
    suspend fun login(request: LoginParams): AuthTokens
    suspend fun register(request: RegisterParams): AuthTokens
    suspend fun refreshToken(refresh: String): AuthTokens
    suspend fun verifyToken(token: String): Boolean
    suspend fun logout()
}
