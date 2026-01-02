package com.example.fuelfit.routine.api.dayDetail.repository

import com.example.fuelfit.routine.api.dayDetail.model.SlotEntry
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntryRequest

interface SlotEntryRepository {

    suspend fun getEntries(slotId: Int): List<SlotEntry>

    suspend fun createEntry(request: SlotEntryRequest): SlotEntry

    suspend fun updateEntry(id: Int, request: SlotEntryRequest): SlotEntry

    suspend fun deleteEntry(id: Int)
}
