package com.example.fuelfit.auth.impl.domain.usecase

import com.example.fuelfit.auth.api.model.AuthToken
import com.example.fuelfit.auth.api.model.LoginParams
import com.example.fuelfit.auth.api.repository.AuthRepository
import com.example.fuelfit.auth.api.usecase.LoginUseCase
import com.example.fuelfit.model.ResultWrapper

internal class LoginUseCaseImpl(
    private val repository: AuthRepository
) : LoginUseCase {
    override suspend fun invoke(request: LoginParams): ResultWrapper<AuthToken> {
        return repository.login(request)
    }
}
