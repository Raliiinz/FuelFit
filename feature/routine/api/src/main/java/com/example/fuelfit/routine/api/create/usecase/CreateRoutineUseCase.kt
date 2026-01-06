package com.example.fuelfit.routine.api.create.usecase

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.create.model.RoutineRequest
import com.example.fuelfit.routine.api.common.Routine

interface CreateRoutineUseCase {
    suspend operator fun invoke(request: RoutineRequest): ResultWrapper<Routine>
}
