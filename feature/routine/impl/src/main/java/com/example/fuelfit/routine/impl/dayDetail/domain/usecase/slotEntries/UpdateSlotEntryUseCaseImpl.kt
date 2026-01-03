package com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slotEntries

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntry
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntryRequest
import com.example.fuelfit.routine.api.dayDetail.repository.DayDetailRepository
import com.example.fuelfit.routine.api.dayDetail.usecase.slotEntries.UpdateSlotEntryUseCase

internal class UpdateSlotEntryUseCaseImpl(
    private val repository: DayDetailRepository
) : UpdateSlotEntryUseCase {
    override suspend fun invoke(id: Int, request: SlotEntryRequest): ResultWrapper<SlotEntry> {
        return repository.updateSlotEntry(id, request)
    }
}
