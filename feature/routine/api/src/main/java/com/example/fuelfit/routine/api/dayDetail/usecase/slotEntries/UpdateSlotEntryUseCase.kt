package com.example.fuelfit.routine.api.dayDetail.usecase.slotEntries

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntry
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntryRequest

interface UpdateSlotEntryUseCase {
    suspend operator fun invoke(id: Int, request: SlotEntryRequest): ResultWrapper<SlotEntry>
}
