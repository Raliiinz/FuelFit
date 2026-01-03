package com.example.fuelfit.routine.api.dayDetail.usecase.slotEntries

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntry

interface GetSlotEntriesUseCase {
    suspend operator fun invoke(slotId: Int): ResultWrapper<List<SlotEntry>>
}
