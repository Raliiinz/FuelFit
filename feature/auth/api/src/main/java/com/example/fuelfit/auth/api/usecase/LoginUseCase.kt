package com.example.fuelfit.auth.api.usecase

import com.example.fuelfit.auth.api.model.AuthTokens
import com.example.fuelfit.auth.api.model.LoginParams

interface LoginUseCase {
    suspend operator fun invoke(request: LoginParams): AuthTokens
}
