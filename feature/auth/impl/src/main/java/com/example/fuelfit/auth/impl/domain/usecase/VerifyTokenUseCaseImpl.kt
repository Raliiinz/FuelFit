package com.example.fuelfit.auth.impl.domain.usecase

import com.example.fuelfit.auth.api.repository.AuthRepository
import com.example.fuelfit.auth.api.usecase.VerifyTokenUseCase

class VerifyTokenUseCaseImpl internal constructor(
    private val repository: AuthRepository
) : VerifyTokenUseCase {
    override suspend fun invoke(token: String): Boolean =
        repository.verifyToken(token)
}

