package com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slotEntries

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntry
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntryRequest
import com.example.fuelfit.routine.api.dayDetail.repository.DayDetailRepository
import com.example.fuelfit.routine.api.dayDetail.usecase.slotEntries.CreateSlotEntryUseCase

internal class CreateSlotEntryUseCaseImpl(
    private val repository: DayDetailRepository
) : CreateSlotEntryUseCase {
    override suspend fun invoke(request: SlotEntryRequest): ResultWrapper<SlotEntry> {
        return repository.createSlotEntry(request)
    }
}
