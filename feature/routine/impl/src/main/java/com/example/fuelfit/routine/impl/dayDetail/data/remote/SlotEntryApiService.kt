package com.example.fuelfit.routine.impl.dayDetail.data.remote

import com.example.fuelfit.routine.impl.common.dto.PaginatedResponseDto
import com.example.fuelfit.routine.impl.dayDetail.data.remote.dto.SlotEntryDto
import com.example.fuelfit.routine.impl.dayDetail.data.remote.dto.SlotEntryRequestDto
import retrofit2.http.*

internal interface SlotEntryApiService {

    @GET("/api/v2/slot-entry/")
    suspend fun getSlotEntries(
        @Query("slot") slotId: Int,
        @Query("exercise") exerciseId: Int? = null,
        @Query("type") type: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("ordering") ordering: String? = null
    ): PaginatedResponseDto<SlotEntryDto>

    @POST("/api/v2/slot-entry/")
    suspend fun createSlotEntry(
        @Body body: SlotEntryRequestDto
    ): SlotEntryDto

    @PUT("/api/v2/slot-entry/{id}/")
    suspend fun updateSlotEntry(
        @Path("id") id: Int,
        @Body body: SlotEntryRequestDto
    ): SlotEntryDto

    @DELETE("/api/v2/slot-entry/{id}/")
    suspend fun deleteSlotEntry(
        @Path("id") id: Int
    )
}
