package com.example.fuelfit.routine.api.dayDetail.usecase.slots

import com.example.fuelfit.model.ResultWrapper

interface DeleteSlotUseCase {
    suspend operator fun invoke(id: Int): ResultWrapper<Unit>
}
