package com.example.fuelfit.routine.api.create.usecase

import com.example.fuelfit.routine.api.common.RoutineRequest
import com.example.fuelfit.routine.api.common.Routine

interface CreateRoutineUseCase {
    suspend operator fun invoke(request: RoutineRequest): Routine
}