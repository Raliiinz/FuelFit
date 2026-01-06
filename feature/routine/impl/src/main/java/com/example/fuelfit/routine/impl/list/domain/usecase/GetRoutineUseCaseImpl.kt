package com.example.fuelfit.routine.impl.list.domain.usecase

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.common.Routine
import com.example.fuelfit.routine.api.list.repository.RoutinesRepository
import com.example.fuelfit.routine.api.list.usecase.GetRoutineUseCase

internal class GetRoutineUseCaseImpl(
    private val repository: RoutinesRepository
) : GetRoutineUseCase {
    override suspend fun invoke(id: Int): ResultWrapper<Routine> {
        return repository.getRoutine(id)
    }
}
