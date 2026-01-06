package com.example.fuelfit.routine.impl.list.domain.usecase

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.list.repository.RoutinesRepository
import com.example.fuelfit.routine.api.list.usecase.DeleteRoutineUseCase

internal class DeleteRoutineUseCaseImpl(
    private val repository: RoutinesRepository
) : DeleteRoutineUseCase {
    override suspend fun invoke(id: Int): ResultWrapper<Unit> {
        return repository.deleteRoutine(id)
    }
}
