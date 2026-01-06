package com.example.fuelfit.exercise.api.repository

import com.example.fuelfit.exercise.api.model.ExerciseCategory
import com.example.fuelfit.exercise.api.model.ExerciseInfo
import com.example.fuelfit.exercise.api.model.ExerciseList
import com.example.fuelfit.exercise.api.model.ExerciseSearchItem
import com.example.fuelfit.model.ResultWrapper

interface ExerciseRepository {

    suspend fun getExercises(
        limit: Int? = null,
        offset: Int? = null,
        categories: List<Int>? = null,
        muscles: List<Int>? = null,
        musclesSecondary: List<Int>? = null,
        equipment: List<Int>? = null,
        ordering: String? = null
    ): ResultWrapper<ExerciseList>

    suspend fun getExerciseById(id: Int): ResultWrapper<ExerciseInfo>

    suspend fun getExerciseCategories(
        limit: Int? = null,
        offset: Int? = null,
        name: String? = null,
        ordering: String? = null
    ): ResultWrapper<List<ExerciseCategory>>

    suspend fun searchExercises(term: String, language: String = "en,ru"): ResultWrapper<List<ExerciseSearchItem>>
}
