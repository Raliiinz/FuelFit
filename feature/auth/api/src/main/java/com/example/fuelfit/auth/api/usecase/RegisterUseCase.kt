package com.example.fuelfit.auth.api.usecase

import com.example.fuelfit.auth.api.model.AuthToken
import com.example.fuelfit.auth.api.model.RegisterParams
import com.example.fuelfit.model.ResultWrapper

interface RegisterUseCase {
    suspend operator fun invoke(request: RegisterParams): ResultWrapper<AuthToken>
}
