package com.example.fuelfit.routine.api.details.usecase

import com.example.fuelfit.routine.api.details.model.RoutineDay

interface GetRoutineDayUseCase {
    suspend operator fun invoke(dayId: Int): RoutineDay
}
