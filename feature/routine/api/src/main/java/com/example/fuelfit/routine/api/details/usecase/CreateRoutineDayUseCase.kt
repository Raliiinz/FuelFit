package com.example.fuelfit.routine.api.details.usecase

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.details.model.RoutineDay
import com.example.fuelfit.routine.api.details.model.RoutineDayRequest

interface CreateRoutineDayUseCase {
    suspend operator fun invoke(day: RoutineDayRequest): ResultWrapper<RoutineDay>
}
