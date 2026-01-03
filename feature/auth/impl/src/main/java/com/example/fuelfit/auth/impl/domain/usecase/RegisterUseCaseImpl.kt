package com.example.fuelfit.auth.impl.domain.usecase

import com.example.fuelfit.auth.api.model.AuthTokens
import com.example.fuelfit.auth.api.model.RegisterParams
import com.example.fuelfit.auth.api.repository.AuthRepository
import com.example.fuelfit.auth.api.usecase.RegisterUseCase
import com.example.fuelfit.model.ResultWrapper

internal class RegisterUseCaseImpl(
    private val repository: AuthRepository
) : RegisterUseCase {
    override suspend fun invoke(request: RegisterParams): ResultWrapper<AuthTokens> {
        return repository.register(request)
    }
}