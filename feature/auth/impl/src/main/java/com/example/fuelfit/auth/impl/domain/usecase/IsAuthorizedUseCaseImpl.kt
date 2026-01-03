package com.example.fuelfit.auth.impl.domain.usecase

import com.example.fuelfit.auth.api.repository.AuthRepository
import com.example.fuelfit.auth.api.usecase.IsAuthorizedUseCase
import com.example.fuelfit.model.ResultWrapper

internal class IsAuthorizedUseCaseImpl(
    private val repository: AuthRepository
) : IsAuthorizedUseCase {
    override suspend fun invoke(): ResultWrapper<Boolean> {
        return repository.isAuthorized()
    }
}
