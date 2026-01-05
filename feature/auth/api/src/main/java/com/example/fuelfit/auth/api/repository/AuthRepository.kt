package com.example.fuelfit.auth.api.repository

import com.example.fuelfit.auth.api.model.AuthToken
import com.example.fuelfit.auth.api.model.LoginParams
import com.example.fuelfit.auth.api.model.RegisterParams
import com.example.fuelfit.model.ResultWrapper

interface AuthRepository {
    suspend fun login(request: LoginParams): ResultWrapper<AuthToken>
    suspend fun register(request: RegisterParams): ResultWrapper<AuthToken>
    suspend fun isAuthorized(): ResultWrapper<Boolean>
    suspend fun logout(): ResultWrapper<Unit>
}
