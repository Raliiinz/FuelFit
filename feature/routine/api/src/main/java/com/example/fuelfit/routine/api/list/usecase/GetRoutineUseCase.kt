package com.example.fuelfit.routine.api.list.usecase

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.common.Routine

interface GetRoutineUseCase {
    suspend operator fun invoke(id: Int): ResultWrapper<Routine>
}
