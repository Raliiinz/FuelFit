package com.example.fuelfit.routine.impl.create.domain.usecase

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.common.Routine
import com.example.fuelfit.routine.api.common.RoutineRequest
import com.example.fuelfit.routine.api.create.repository.CreateRoutineRepository
import com.example.fuelfit.routine.api.create.usecase.CreateRoutineUseCase

internal class CreateRoutineUseCaseImpl(
    private val repository: CreateRoutineRepository
) : CreateRoutineUseCase {

    override suspend fun invoke(request: RoutineRequest): ResultWrapper<Routine> {
        return repository.createRoutine(request)
    }
}