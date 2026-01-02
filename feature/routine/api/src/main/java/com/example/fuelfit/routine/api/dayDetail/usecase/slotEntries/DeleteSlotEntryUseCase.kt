package com.example.fuelfit.routine.api.dayDetail.usecase.slotEntries

interface DeleteSlotEntryUseCase {
    suspend operator fun invoke(id: Int)
}
