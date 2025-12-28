package com.example.fuelfit.auth.impl.domain.usecase

import com.example.fuelfit.auth.api.model.AuthTokens
import com.example.fuelfit.auth.api.model.LoginParams
import com.example.fuelfit.auth.api.repository.AuthRepository
import com.example.fuelfit.auth.api.usecase.LoginUseCase

internal class LoginUseCaseImpl(
    private val repository: AuthRepository
) : LoginUseCase {
    override suspend fun invoke(request: LoginParams): AuthTokens {
        return repository.login(request)
    }
}
