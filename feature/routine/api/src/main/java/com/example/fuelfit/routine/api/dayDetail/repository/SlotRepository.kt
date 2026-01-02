package com.example.fuelfit.routine.api.dayDetail.repository

import com.example.fuelfit.routine.api.dayDetail.model.Slot
import com.example.fuelfit.routine.api.dayDetail.model.SlotRequest

interface SlotRepository {

    suspend fun getSlots(dayId: Int): List<Slot>

    suspend fun createSlot(request: SlotRequest): Slot

    suspend fun updateSlot(id: Int, request: SlotRequest): Slot

    suspend fun deleteSlot(id: Int)
}
