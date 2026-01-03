package com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slots

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.model.Slot
import com.example.fuelfit.routine.api.dayDetail.repository.DayDetailRepository
import com.example.fuelfit.routine.api.dayDetail.usecase.slots.GetSlotsUseCase

internal class GetSlotsUseCaseImpl(
    private val repository: DayDetailRepository
) : GetSlotsUseCase {
    override suspend fun invoke(dayId: Int): ResultWrapper<List<Slot>> {
        return repository.getSlots(dayId)
    }
}
