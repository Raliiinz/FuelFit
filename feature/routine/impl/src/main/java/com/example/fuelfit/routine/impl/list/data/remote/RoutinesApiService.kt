package com.example.fuelfit.routine.impl.list.data.remote

import com.example.fuelfit.routine.impl.common.dto.PaginatedResponseDto
import com.example.fuelfit.routine.impl.common.dto.RoutineDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface RoutinesApiService {

    @GET("/api/v2/routine/")
    suspend fun getRoutines(
        @Query("is_public") isPublic: Boolean? = false
    ): PaginatedResponseDto<RoutineDto>

    @GET("/api/v2/routine/{id}/")
    suspend fun getRoutine(@Path("id") id: Int): RoutineDto

    @DELETE("/api/v2/routine/{id}/")
    suspend fun deleteRoutine(@Path("id") id: Int)
}
