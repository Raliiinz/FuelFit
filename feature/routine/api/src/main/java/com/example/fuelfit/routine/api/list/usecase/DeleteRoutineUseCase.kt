package com.example.fuelfit.routine.api.list.usecase

import com.example.fuelfit.model.ResultWrapper

interface DeleteRoutineUseCase {
    suspend operator fun invoke(id: Int): ResultWrapper<Unit>
}
