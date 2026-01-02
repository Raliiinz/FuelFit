package com.example.fuelfit.routine.api.dayDetail.usecase.slots

import com.example.fuelfit.routine.api.dayDetail.model.Slot
import com.example.fuelfit.routine.api.dayDetail.model.SlotRequest

interface CreateSlotUseCase {
    suspend operator fun invoke(request: SlotRequest): Slot
}
