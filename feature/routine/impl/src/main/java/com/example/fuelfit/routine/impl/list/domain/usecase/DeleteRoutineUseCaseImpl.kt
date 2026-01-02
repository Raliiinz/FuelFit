package com.example.fuelfit.routine.impl.list.domain.usecase

import com.example.fuelfit.routine.api.list.repository.RoutineRepository
import com.example.fuelfit.routine.api.list.usecase.DeleteRoutineUseCase

class DeleteRoutineUseCaseImpl(
    private val repository: RoutineRepository
) : DeleteRoutineUseCase {
    override suspend fun invoke(id: Int) {
        return repository.deleteRoutine(id)
    }
}
