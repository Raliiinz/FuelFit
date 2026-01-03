package com.example.fuelfit.auth.api.usecase

import com.example.fuelfit.model.ResultWrapper

interface VerifyTokenUseCase {
    suspend operator fun invoke(token: String): ResultWrapper<Boolean>
}

