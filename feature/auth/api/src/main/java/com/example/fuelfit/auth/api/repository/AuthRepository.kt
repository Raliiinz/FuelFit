package com.example.fuelfit.auth.api.repository

import com.example.fuelfit.auth.api.model.AuthTokens
import com.example.fuelfit.auth.api.model.LoginParams
import com.example.fuelfit.auth.api.model.RegisterParams
import com.example.fuelfit.model.ResultWrapper

interface AuthRepository {
    suspend fun login(request: LoginParams): ResultWrapper<AuthTokens>
    suspend fun register(request: RegisterParams): ResultWrapper<AuthTokens>
    suspend fun refreshToken(refresh: String): ResultWrapper<AuthTokens>
    suspend fun verifyToken(token: String): ResultWrapper<Boolean>
    suspend fun isAuthorized(): ResultWrapper<Boolean>
    suspend fun logout(): ResultWrapper<Unit>
}
