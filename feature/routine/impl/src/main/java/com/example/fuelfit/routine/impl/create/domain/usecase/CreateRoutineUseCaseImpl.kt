package com.example.fuelfit.routine.impl.create.domain.usecase

import com.example.fuelfit.routine.api.common.Routine
import com.example.fuelfit.routine.api.common.RoutineRequest
import com.example.fuelfit.routine.api.create.repository.CreateRoutineRepository
import com.example.fuelfit.routine.api.create.usecase.CreateRoutineUseCase

class CreateRoutineUseCaseImpl(
    private val repository: CreateRoutineRepository
) : CreateRoutineUseCase {

    override suspend fun invoke(request: RoutineRequest): Routine {
        return repository.createRoutine(request)
    }
}