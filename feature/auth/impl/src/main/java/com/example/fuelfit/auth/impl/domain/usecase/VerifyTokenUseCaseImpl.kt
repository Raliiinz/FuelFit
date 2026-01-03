package com.example.fuelfit.auth.impl.domain.usecase

import com.example.fuelfit.auth.api.repository.AuthRepository
import com.example.fuelfit.auth.api.usecase.VerifyTokenUseCase
import com.example.fuelfit.model.ResultWrapper

internal class VerifyTokenUseCaseImpl internal constructor(
    private val repository: AuthRepository
) : VerifyTokenUseCase {
    override suspend fun invoke(token: String): ResultWrapper<Boolean> {
        return repository.verifyToken(token)
    }
}

