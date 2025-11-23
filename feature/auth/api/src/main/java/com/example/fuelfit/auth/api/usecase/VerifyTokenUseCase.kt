package com.example.fuelfit.auth.api.usecase

interface VerifyTokenUseCase {
    suspend operator fun invoke(token: String): Boolean
}

