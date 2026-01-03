package com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slotEntries

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntry
import com.example.fuelfit.routine.api.dayDetail.repository.DayDetailRepository
import com.example.fuelfit.routine.api.dayDetail.usecase.slotEntries.GetSlotEntriesUseCase

internal class GetSlotEntriesUseCaseImpl(
    private val repository: DayDetailRepository
) : GetSlotEntriesUseCase {
    override suspend fun invoke(slotId: Int): ResultWrapper<List<SlotEntry>> {
        return repository.getSlotEntries(slotId)
    }
}
