package com.example.fuelfit.exercise.impl.data.remote

import com.example.fuelfit.exercise.impl.data.remote.dto.ExerciseCreateRequest
import com.example.fuelfit.exercise.impl.data.remote.dto.ExerciseDto
import com.example.fuelfit.exercise.impl.data.remote.dto.ExerciseListResponseDto
import com.example.fuelfit.exercise.impl.data.remote.dto.ExerciseUpdateRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ExerciseApiService {

    @GET("api/v2/exercise/")
    suspend fun getExercises(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("category") category: Int? = null,
        @Query("muscles") muscles: List<Int>? = null,
        @Query("muscles_secondary") musclesSecondary: List<Int>? = null,
        @Query("equipment") equipment: List<Int>? = null,
        @Query("ordering") ordering: String? = null
    ): ExerciseListResponseDto

    @GET("api/v2/exercise/{id}/")
    suspend fun getExerciseById(@Path("id") id: Int): ExerciseDto

    @POST("api/v2/exercise/")
    suspend fun createExercise(@Body body: ExerciseCreateRequest): ExerciseDto

    @PUT("api/v2/exercise/{id}/")
    suspend fun updateExercise(
        @Path("id") id: Int,
        @Body body: ExerciseUpdateRequest
    ): ExerciseDto

    @DELETE("api/v2/exercise/{id}/")
    suspend fun deleteExercise(@Path("id") id: Int)

}