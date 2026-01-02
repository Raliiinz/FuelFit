package com.example.fuelfit.routine.impl.list.domain.usecase

import com.example.fuelfit.routine.api.common.Routine
import com.example.fuelfit.routine.api.list.repository.RoutineRepository
import com.example.fuelfit.routine.api.list.usecase.GetRoutineUseCase

class GetRoutineUseCaseImpl(
    private val repository: RoutineRepository
) : GetRoutineUseCase {
    override suspend fun invoke(id: Int): Routine {
        return repository.getRoutine(id)
    }
}
