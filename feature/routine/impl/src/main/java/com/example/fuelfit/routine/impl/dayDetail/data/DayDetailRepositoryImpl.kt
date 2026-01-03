package com.example.fuelfit.routine.impl.dayDetail.data

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.network.safeApiCall
import com.example.fuelfit.routine.api.dayDetail.model.*
import com.example.fuelfit.routine.api.dayDetail.repository.DayDetailRepository
import com.example.fuelfit.routine.impl.dayDetail.data.mapper.SlotMapper
import com.example.fuelfit.routine.impl.dayDetail.data.remote.SlotApiService
import com.example.fuelfit.routine.impl.dayDetail.data.remote.SlotEntryApiService

internal class DayDetailRepositoryImpl(
    private val slotApi: SlotApiService,
    private val slotEntryApi: SlotEntryApiService,
    private val mapper: SlotMapper
) : DayDetailRepository {

    // ───────── SLOT ─────────

    override suspend fun getSlots(dayId: Int): ResultWrapper<List<Slot>> =
        safeApiCall {
            slotApi.getSlots(dayId)
                .results
                .map(mapper::fromDto)
        }

    override suspend fun createSlot(request: SlotRequest): ResultWrapper<Slot> =
        safeApiCall {
            mapper.fromDto(slotApi.createSlot(mapper.toRequestDto(request)))
        }

    override suspend fun updateSlot(id: Int, request: SlotRequest): ResultWrapper<Slot> =
        safeApiCall {
            mapper.fromDto(slotApi.updateSlot(id, mapper.toRequestDto(request)))
        }

    override suspend fun deleteSlot(id: Int): ResultWrapper<Unit> =
        safeApiCall {
            slotApi.deleteSlot(id)
        }

    // ───────── SLOT ENTRY ─────────

    override suspend fun getSlotEntries(slotId: Int): ResultWrapper<List<SlotEntry>> =
        safeApiCall {
            slotEntryApi.getSlotEntries(slotId)
                .results
                .map(mapper::fromDto)
        }

    override suspend fun createSlotEntry(request: SlotEntryRequest): ResultWrapper<SlotEntry> =
        safeApiCall {
            mapper.fromDto(slotEntryApi.createSlotEntry(mapper.toRequestDto(request)))
        }

    override suspend fun updateSlotEntry(id: Int, request: SlotEntryRequest): ResultWrapper<SlotEntry> =
        safeApiCall {
            mapper.fromDto(slotEntryApi.updateSlotEntry(id, mapper.toRequestDto(request)))
        }

    override suspend fun deleteSlotEntry(id: Int): ResultWrapper<Unit> =
        safeApiCall {
            slotEntryApi.deleteSlotEntry(id)
        }
}
