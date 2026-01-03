package com.example.fuelfit.routine.api.details.usecase

import com.example.fuelfit.model.ResultWrapper

interface DeleteRoutineDayUseCase {
    suspend operator fun invoke(dayId: Int): ResultWrapper<Unit>
}