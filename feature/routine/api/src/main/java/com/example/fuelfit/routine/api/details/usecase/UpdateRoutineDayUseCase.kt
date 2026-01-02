package com.example.fuelfit.routine.api.details.usecase

import com.example.fuelfit.routine.api.details.model.RoutineDay

interface UpdateRoutineDayUseCase {
    suspend operator fun invoke(day: RoutineDay): RoutineDay
}