package com.example.fuelfit.exercise.impl.data

import com.example.fuelfit.exercise.api.model.*
import com.example.fuelfit.exercise.api.repository.ExerciseRepository
import com.example.fuelfit.exercise.impl.data.mapper.ExerciseMapper
import com.example.fuelfit.exercise.impl.data.remote.ExerciseApiService
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.network.safeApiCall

internal class ExerciseRepositoryImpl(
    private val api: ExerciseApiService,
    private val mapper: ExerciseMapper
) : ExerciseRepository {

    override suspend fun getExercises(
        limit: Int?,
        offset: Int?,
        categories: List<Int>?,
        muscles: List<Int>?,
        musclesSecondary: List<Int>?,
        equipment: List<Int>?,
        ordering: String?
    ): ResultWrapper<ExerciseList> = safeApiCall {
        val dto = api.getExercisesInfo(
            limit = limit,
            offset = offset,
            categoryIn = categories,
            muscles = muscles,
            musclesSecondary = musclesSecondary,
            equipment = equipment,
            ordering = ordering
        )
        mapper.mapExerciseList(dto)
    }

    override suspend fun getExerciseById(id: Int): ResultWrapper<ExerciseInfo> = safeApiCall {
        val dto = api.getExerciseInfoById(id)
        mapper.mapExerciseInfo(dto)
    }

    override suspend fun getExerciseCategories(
        limit: Int?,
        offset: Int?,
        name: String?,
        ordering: String?
    ): ResultWrapper<List<ExerciseCategory>> = safeApiCall {
        val dto = api.getExerciseCategories(
            limit = limit,
            offset = offset,
            name = name,
            ordering = ordering
        )
        dto.results.map { mapper.mapCategory(it) }
    }

    override suspend fun searchExercises(
        term: String,
        language: String
    ): ResultWrapper<List<ExerciseSearchItem>> = safeApiCall {
        val dto = api.searchExercises(term, language)
        dto.suggestions.map { mapper.mapSearchItem(it.data) }
    }
}
