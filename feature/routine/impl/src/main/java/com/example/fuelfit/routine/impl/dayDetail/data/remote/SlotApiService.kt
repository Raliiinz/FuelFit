package com.example.fuelfit.routine.impl.dayDetail.data.remote

import com.example.fuelfit.routine.impl.common.dto.PaginatedResponseDto
import com.example.fuelfit.routine.impl.dayDetail.data.remote.dto.SlotDto
import com.example.fuelfit.routine.impl.dayDetail.data.remote.dto.SlotRequestDto
import retrofit2.http.*

internal interface SlotApiService {

    @GET("/api/v2/slot/")
    suspend fun getSlots(
        @Query("day") dayId: Int,
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("ordering") ordering: String? = null
    ): PaginatedResponseDto<SlotDto>

    @GET("/api/v2/slot/{id}/")
    suspend fun getSlot(
        @Path("id") id: Int
    ): SlotDto

    @POST("/api/v2/slot/")
    suspend fun createSlot(
        @Body body: SlotRequestDto
    ): SlotDto

    @PUT("/api/v2/slot/{id}/")
    suspend fun updateSlot(
        @Path("id") id: Int,
        @Body body: SlotRequestDto
    ): SlotDto

    @PATCH("/api/v2/slot/{id}/")
    suspend fun patchSlot(
        @Path("id") id: Int,
        @Body body: SlotRequestDto
    ): SlotDto

    @DELETE("/api/v2/slot/{id}/")
    suspend fun deleteSlot(
        @Path("id") id: Int
    )
}
