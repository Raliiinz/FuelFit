package com.example.fuelfit.routine.api.details.usecase

interface DeleteRoutineDayUseCase {
    suspend operator fun invoke(dayId: Int)
}