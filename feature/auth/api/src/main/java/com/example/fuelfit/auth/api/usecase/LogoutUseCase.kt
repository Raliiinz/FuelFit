package com.example.fuelfit.auth.api.usecase

import com.example.fuelfit.model.ResultWrapper

interface LogoutUseCase {
    suspend operator fun invoke() : ResultWrapper<Unit>
}
