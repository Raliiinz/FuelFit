package com.example.fuelfit.routine.api.dayDetail.usecase.slots

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.model.Slot
import com.example.fuelfit.routine.api.dayDetail.model.SlotRequest

interface UpdateSlotUseCase {
    suspend operator fun invoke(id: Int, request: SlotRequest): ResultWrapper<Slot>
}
