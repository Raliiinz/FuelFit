package com.example.fuelfit.routine.impl.details.domain.usecase

import com.example.fuelfit.routine.api.details.model.RoutineDay
import com.example.fuelfit.routine.api.details.repository.RoutineDayRepository
import com.example.fuelfit.routine.api.details.usecase.GetRoutineDaysUseCase

class GetRoutineDaysUseCaseImpl(
    private val repository: RoutineDayRepository
) : GetRoutineDaysUseCase {

    override suspend fun invoke(routineId: Int): List<RoutineDay> {
        return repository.getDaysByRoutine(routineId)
    }
}