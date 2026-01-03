package com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slots

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.repository.DayDetailRepository
import com.example.fuelfit.routine.api.dayDetail.usecase.slots.DeleteSlotUseCase

internal class DeleteSlotUseCaseImpl(
    private val repository: DayDetailRepository
) : DeleteSlotUseCase {
    override suspend fun invoke(id: Int): ResultWrapper<Unit> {
        return repository.deleteSlot(id)
    }
}
