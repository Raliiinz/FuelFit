package com.example.fuelfit.routine.impl.details.domain.usecase

import com.example.fuelfit.routine.api.details.model.RoutineDay
import com.example.fuelfit.routine.api.details.model.RoutineDayRequest
import com.example.fuelfit.routine.api.details.repository.RoutineDayRepository
import com.example.fuelfit.routine.api.details.usecase.CreateRoutineDayUseCase

class CreateRoutineDayUseCaseImpl(
    private val repository: RoutineDayRepository
) : CreateRoutineDayUseCase {
    override suspend fun invoke(day: RoutineDayRequest): RoutineDay {
        return repository.createDay(day)
    }
}