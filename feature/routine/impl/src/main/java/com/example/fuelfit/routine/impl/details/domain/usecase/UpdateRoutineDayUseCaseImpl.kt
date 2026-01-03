package com.example.fuelfit.routine.impl.details.domain.usecase

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.details.model.RoutineDay
import com.example.fuelfit.routine.api.details.model.RoutineDayRequest
import com.example.fuelfit.routine.api.details.repository.RoutineDayRepository
import com.example.fuelfit.routine.api.details.usecase.UpdateRoutineDayUseCase

internal class UpdateRoutineDayUseCaseImpl(
    private val repository: RoutineDayRepository
) : UpdateRoutineDayUseCase {
    override suspend fun invoke(
        dayRequest: RoutineDayRequest,
        id: Int
    ): ResultWrapper<RoutineDay> {
        return repository.updateDayFromRequest(dayRequest, id)
    }
}
