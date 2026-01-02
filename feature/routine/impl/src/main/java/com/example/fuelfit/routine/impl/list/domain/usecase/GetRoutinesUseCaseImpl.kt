package com.example.fuelfit.routine.impl.list.domain.usecase

import com.example.fuelfit.routine.api.common.Routine
import com.example.fuelfit.routine.api.list.repository.RoutineRepository
import com.example.fuelfit.routine.api.list.usecase.GetRoutinesUseCase

class GetRoutinesUseCaseImpl(
    private val repository: RoutineRepository
) : GetRoutinesUseCase {
    override suspend fun invoke(): List<Routine> {
        return repository.getRoutines()
    }
}
