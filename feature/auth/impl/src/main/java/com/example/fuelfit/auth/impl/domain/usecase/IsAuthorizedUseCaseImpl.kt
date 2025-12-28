package com.example.fuelfit.auth.impl.domain.usecase

import com.example.fuelfit.auth.api.repository.AuthRepository
import com.example.fuelfit.auth.api.usecase.IsAuthorizedUseCase

internal class IsAuthorizedUseCaseImpl(
    private val repository: AuthRepository
) : IsAuthorizedUseCase {
    override suspend fun invoke(): Boolean {
        return repository.isAuthorized()
    }
}
