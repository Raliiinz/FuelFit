package com.example.fuelfit.routine.api.dayDetail.usecase.slotEntries

import com.example.fuelfit.routine.api.dayDetail.model.SlotEntry
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntryRequest

interface CreateSlotEntryUseCase {
    suspend operator fun invoke(request: SlotEntryRequest): SlotEntry
}
