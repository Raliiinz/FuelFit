package com.example.fuelfit.routine.impl.details.data.remote

import com.example.fuelfit.routine.impl.details.data.remote.dto.RoutineDayDto
import com.example.fuelfit.routine.impl.details.data.remote.dto.RoutineDayListResponseDto
import com.example.fuelfit.routine.impl.details.data.remote.dto.RoutineDayRequestDto
import retrofit2.http.*

interface RoutineDayApiService {

    @GET("/api/v2/day/")
    suspend fun getDays(
        @Query("routine") routineId: Int,
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 0
    ): RoutineDayListResponseDto

    @GET("/api/v2/day/{id}/")
    suspend fun getDay(
        @Path("id") id: Int
    ): RoutineDayDto

    @POST("/api/v2/day/")
    suspend fun createDay(
        @Body body: RoutineDayRequestDto
    ): RoutineDayDto

    @PUT("/api/v2/day/{id}/")
    suspend fun updateDay(
        @Path("id") id: Int,
        @Body body: RoutineDayRequestDto
    ): RoutineDayDto

    @DELETE("/api/v2/day/{id}/")
    suspend fun deleteDay(
        @Path("id") id: Int
    )
}
