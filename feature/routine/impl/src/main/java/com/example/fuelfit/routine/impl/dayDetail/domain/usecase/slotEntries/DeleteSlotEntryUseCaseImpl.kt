package com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slotEntries

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.repository.DayDetailRepository
import com.example.fuelfit.routine.api.dayDetail.usecase.slotEntries.DeleteSlotEntryUseCase

internal class DeleteSlotEntryUseCaseImpl(
    private val repository: DayDetailRepository
) : DeleteSlotEntryUseCase {
    override suspend fun invoke(id: Int): ResultWrapper<Unit> {
        return repository.deleteSlotEntry(id)
    }
}
