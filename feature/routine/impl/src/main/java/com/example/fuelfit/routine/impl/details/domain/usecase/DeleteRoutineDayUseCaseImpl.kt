package com.example.fuelfit.routine.impl.details.domain.usecase

import com.example.fuelfit.routine.api.details.repository.RoutineDayRepository
import com.example.fuelfit.routine.api.details.usecase.DeleteRoutineDayUseCase

class DeleteRoutineDayUseCaseImpl(
    private val repository: RoutineDayRepository
) : DeleteRoutineDayUseCase {

    override suspend fun invoke(dayId: Int) =
        repository.deleteDay(dayId)
}
