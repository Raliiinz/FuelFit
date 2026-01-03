package com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slots

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.model.Slot
import com.example.fuelfit.routine.api.dayDetail.model.SlotRequest
import com.example.fuelfit.routine.api.dayDetail.repository.DayDetailRepository
import com.example.fuelfit.routine.api.dayDetail.usecase.slots.CreateSlotUseCase

internal class CreateSlotUseCaseImpl(
    private val repository: DayDetailRepository
) : CreateSlotUseCase {
    override suspend fun invoke(request: SlotRequest): ResultWrapper<Slot> {
        return repository.createSlot(request)
    }
}
