package com.example.fuelfit.routine.impl.create.domain.usecase

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.common.Routine
import com.example.fuelfit.routine.api.common.RoutineRequest
import com.example.fuelfit.routine.api.create.repository.CreateRoutineRepository
import com.example.fuelfit.routine.api.create.usecase.UpdateRoutineUseCase

internal class UpdateRoutineUseCaseImpl(
    private val repository: CreateRoutineRepository
) : UpdateRoutineUseCase {
    override suspend fun invoke(
        id: Int,
        request: RoutineRequest
    ): ResultWrapper<Routine> {
        return repository.updateRoutine(id, request)
    }
}