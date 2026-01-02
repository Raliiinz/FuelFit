package com.example.fuelfit.routine.api.details.usecase

import com.example.fuelfit.routine.api.details.model.RoutineDay

interface GetRoutineDaysUseCase {
    suspend operator fun invoke(routineId: Int): List<RoutineDay>
}
