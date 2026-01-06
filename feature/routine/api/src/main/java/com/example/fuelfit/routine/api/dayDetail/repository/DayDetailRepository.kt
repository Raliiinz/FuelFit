package com.example.fuelfit.routine.api.dayDetail.repository

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.routine.api.dayDetail.model.Slot
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntry
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntryRequest
import com.example.fuelfit.routine.api.dayDetail.model.SlotRequest

interface DayDetailRepository {

    // SLOT
    suspend fun getSlots(dayId: Int): ResultWrapper<List<Slot>>
    suspend fun createSlot(request: SlotRequest): ResultWrapper<Slot>
    suspend fun updateSlot(id: Int, request: SlotRequest): ResultWrapper<Slot>
    suspend fun deleteSlot(id: Int): ResultWrapper<Unit>

    // SLOT ENTRY
    suspend fun getSlotEntries(slotId: Int): ResultWrapper<List<SlotEntry>>
    suspend fun createSlotEntry(request: SlotEntryRequest): ResultWrapper<SlotEntry>
    suspend fun updateSlotEntry(id: Int, request: SlotEntryRequest): ResultWrapper<SlotEntry>
    suspend fun deleteSlotEntry(id: Int): ResultWrapper<Unit>
}
