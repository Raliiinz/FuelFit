package com.example.fuelfit.auth.api.usecase

interface IsAuthorizedUseCase {
    suspend operator fun invoke(): Boolean
}
