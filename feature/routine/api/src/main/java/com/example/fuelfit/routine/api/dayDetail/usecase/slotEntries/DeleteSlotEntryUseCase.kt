package com.example.fuelfit.routine.api.dayDetail.usecase.slotEntries

import com.example.fuelfit.model.ResultWrapper

interface DeleteSlotEntryUseCase {
    suspend operator fun invoke(id: Int): ResultWrapper<Unit>
}
