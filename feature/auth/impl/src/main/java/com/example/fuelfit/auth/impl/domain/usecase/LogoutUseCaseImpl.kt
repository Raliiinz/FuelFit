package com.example.fuelfit.auth.impl.domain.usecase

import com.example.fuelfit.auth.api.repository.AuthRepository
import com.example.fuelfit.auth.api.usecase.LogoutUseCase

internal class LogoutUseCaseImpl(
    private val repository: AuthRepository
) : LogoutUseCase {
    override suspend fun invoke() {
        return repository.logout()
    }
}
