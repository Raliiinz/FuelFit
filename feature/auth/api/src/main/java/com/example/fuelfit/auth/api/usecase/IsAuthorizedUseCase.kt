package com.example.fuelfit.auth.api.usecase

import com.example.fuelfit.model.ResultWrapper

interface IsAuthorizedUseCase {
    suspend operator fun invoke(): ResultWrapper<Boolean>
}
