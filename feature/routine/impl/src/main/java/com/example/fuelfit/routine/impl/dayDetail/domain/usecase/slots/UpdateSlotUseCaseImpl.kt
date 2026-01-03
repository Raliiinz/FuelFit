package com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slots

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.model.Slot
import com.example.fuelfit.routine.api.dayDetail.model.SlotRequest
import com.example.fuelfit.routine.api.dayDetail.repository.DayDetailRepository
import com.example.fuelfit.routine.api.dayDetail.usecase.slots.UpdateSlotUseCase

internal class UpdateSlotUseCaseImpl(
    private val repository: DayDetailRepository
) : UpdateSlotUseCase {
    override suspend fun invoke(id: Int, request: SlotRequest): ResultWrapper<Slot> {
        return repository.updateSlot(id, request)
    }
}
