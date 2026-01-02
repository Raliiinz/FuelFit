package com.example.fuelfit.routine.api.dayDetail.usecase.slots

import com.example.fuelfit.routine.api.dayDetail.model.Slot

interface GetSlotsUseCase {
    suspend operator fun invoke(dayId: Int): List<Slot>
}
