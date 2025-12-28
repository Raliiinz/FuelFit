package com.example.fuelfit.exercise.api.repository

import com.example.fuelfit.exercise.api.model.ExerciseCategory
import com.example.fuelfit.exercise.api.model.ExerciseInfo
import com.example.fuelfit.exercise.api.model.ExerciseList

interface ExerciseRepository {
    suspend fun getExercises(
        limit: Int? = null,
        offset: Int? = null,
        categories: List<Int>? = null,
        muscles: List<Int>? = null,
        musclesSecondary: List<Int>? = null,
        equipment: List<Int>? = null,
        ordering: String? = null
    ): ExerciseList

    suspend fun getExerciseById(id: Int): ExerciseInfo

    suspend fun getExerciseCategories(
        limit: Int? = null,
        offset: Int? = null,
        name: String? = null,
        ordering: String? = null
    ): List<ExerciseCategory>
}
