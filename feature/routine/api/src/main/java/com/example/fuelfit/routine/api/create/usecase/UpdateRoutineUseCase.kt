package com.example.fuelfit.routine.api.create.usecase

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.create.model.RoutineRequest
import com.example.fuelfit.routine.api.common.Routine

interface UpdateRoutineUseCase {
    suspend operator fun invoke(id: Int, request: RoutineRequest): ResultWrapper<Routine>
}
