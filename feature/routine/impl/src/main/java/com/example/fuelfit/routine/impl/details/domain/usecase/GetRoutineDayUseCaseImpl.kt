package com.example.fuelfit.routine.impl.details.domain.usecase

import com.example.fuelfit.routine.api.details.repository.RoutineDayRepository
import com.example.fuelfit.routine.api.details.usecase.GetRoutineDayUseCase

class GetRoutineDayUseCaseImpl(
    private val repository: RoutineDayRepository
) : GetRoutineDayUseCase {

    override suspend fun invoke(dayId: Int) =
        repository.getDay(dayId)
}