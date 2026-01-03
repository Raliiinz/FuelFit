package com.example.fuelfit.exercise.impl.data.remote

import com.example.fuelfit.exercise.impl.data.remote.dto.CategoryListResponseDto
import com.example.fuelfit.exercise.impl.data.remote.dto.ExerciseInfoDto
import com.example.fuelfit.exercise.impl.data.remote.dto.ExerciseInfoListResponseDto
import com.example.fuelfit.exercise.impl.data.remote.dto.ExerciseSearchResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ExerciseApiService {

    @GET("api/v2/exerciseinfo/")
    suspend fun getExercisesInfo(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,

        @Query("category") category: Int? = null,
        @Query("category__in") categoryIn: List<Int>? = null,

        @Query("equipment") equipment: List<Int>? = null,
        @Query("equipment__in") equipmentIn: List<Int>? = null,

        @Query("muscles") muscles: List<Int>? = null,
        @Query("muscles__in") musclesIn: List<Int>? = null,

        @Query("muscles_secondary") musclesSecondary: List<Int>? = null,
        @Query("muscles_secondary__in") musclesSecondaryIn: List<Int>? = null,

        @Query("name__search") nameSearch: String? = null,
        @Query("language__code") languageCode: String? = null,

        @Query("ordering") ordering: String? = null,
        @Query("id") id: Int? = null,
        @Query("id__in") idIn: List<Int>? = null,
        @Query("uuid") uuid: String? = null
    ): ExerciseInfoListResponseDto

    @GET("api/v2/exerciseinfo/{id}/")
    suspend fun getExerciseInfoById(@Path("id") id: Int): ExerciseInfoDto

    @GET("api/v2/exercisecategory/")
    suspend fun getExerciseCategories(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("name") name: String? = null,
        @Query("ordering") ordering: String? = null
    ): CategoryListResponseDto

    @GET("/api/v2/exercise/search/")
    suspend fun searchExercises(
        @Query("term") term: String,
        @Query("language") language: String = "en"
    ): ExerciseSearchResponseDto
}