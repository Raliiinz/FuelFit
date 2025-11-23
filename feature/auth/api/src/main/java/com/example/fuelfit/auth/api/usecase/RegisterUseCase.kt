package com.example.fuelfit.auth.api.usecase

import com.example.fuelfit.auth.api.model.AuthTokens
import com.example.fuelfit.auth.api.model.RegisterParams

interface RegisterUseCase {
    suspend operator fun invoke(request: RegisterParams): AuthTokens
}