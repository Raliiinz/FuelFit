package com.example.fuelfit.routine.impl.list.domain.usecase

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.common.Routine
import com.example.fuelfit.routine.api.list.repository.RoutinesRepository
import com.example.fuelfit.routine.api.list.usecase.GetRoutinesUseCase

internal class GetRoutinesUseCaseImpl(
    private val repository: RoutinesRepository
) : GetRoutinesUseCase {
    override suspend fun invoke(): ResultWrapper<List<Routine>> {
        return repository.getRoutines()
    }
}
