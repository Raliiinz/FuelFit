package com.example.fuelfit.routine.impl.create.data.remote

import com.example.fuelfit.routine.impl.common.dto.RoutineDto
import com.example.fuelfit.routine.impl.common.dto.RoutineRequestDto
import retrofit2.http.*

interface CreateRoutineApiService {

    @POST("/api/v2/routine/")
    suspend fun createRoutine(
        @Body body: RoutineRequestDto
    ): RoutineDto

    @PUT("/api/v2/routine/{id}/")
    suspend fun updateRoutine(
        @Path("id") id: Int,
        @Body body: RoutineRequestDto
    ): RoutineDto
}
