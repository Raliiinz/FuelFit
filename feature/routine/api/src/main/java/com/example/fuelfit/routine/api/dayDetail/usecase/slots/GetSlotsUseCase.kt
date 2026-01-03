package com.example.fuelfit.routine.api.dayDetail.usecase.slots

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.model.Slot

interface GetSlotsUseCase {
    suspend operator fun invoke(dayId: Int): ResultWrapper<List<Slot>>
}
