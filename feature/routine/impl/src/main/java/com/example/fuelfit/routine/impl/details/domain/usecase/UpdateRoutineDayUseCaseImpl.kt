package com.example.fuelfit.routine.impl.details.domain.usecase

import com.example.fuelfit.routine.api.details.model.RoutineDay
import com.example.fuelfit.routine.api.details.repository.RoutineDayRepository
import com.example.fuelfit.routine.api.details.usecase.UpdateRoutineDayUseCase

class UpdateRoutineDayUseCaseImpl(
    private val repository: RoutineDayRepository
) : UpdateRoutineDayUseCase {

    override suspend fun invoke(day: RoutineDay) =
        repository.updateDay(day)
}
