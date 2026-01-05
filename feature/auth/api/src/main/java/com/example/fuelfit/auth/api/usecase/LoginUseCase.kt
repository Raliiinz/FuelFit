package com.example.fuelfit.auth.api.usecase

import com.example.fuelfit.auth.api.model.AuthToken
import com.example.fuelfit.auth.api.model.LoginParams
import com.example.fuelfit.model.ResultWrapper

interface LoginUseCase {
    suspend operator fun invoke(request: LoginParams): ResultWrapper<AuthToken>
}
