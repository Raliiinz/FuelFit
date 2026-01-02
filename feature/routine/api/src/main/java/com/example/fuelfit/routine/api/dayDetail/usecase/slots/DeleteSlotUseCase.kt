package com.example.fuelfit.routine.api.dayDetail.usecase.slots

interface DeleteSlotUseCase {
    suspend operator fun invoke(id: Int)
}
