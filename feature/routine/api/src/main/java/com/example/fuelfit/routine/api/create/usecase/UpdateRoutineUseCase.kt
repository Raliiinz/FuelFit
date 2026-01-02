package com.example.fuelfit.routine.api.create.usecase

import com.example.fuelfit.routine.api.common.RoutineRequest
import com.example.fuelfit.routine.api.common.Routine

interface UpdateRoutineUseCase {
    suspend operator fun invoke(id: Int, request: RoutineRequest): Routine
}