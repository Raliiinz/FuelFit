package com.example.fuelfit.exercise.api.repository

import com.example.fuelfit.exercise.api.model.Exercise
import com.example.fuelfit.exercise.api.model.ExerciseCreate
import com.example.fuelfit.exercise.api.model.ExerciseList
import com.example.fuelfit.exercise.api.model.ExerciseUpdate

interface ExerciseRepository {
    suspend fun getExercises(
        limit: Int? = null,
        offset: Int? = null,
        category: Int? = null,
        muscles: List<Int>? = null,
        musclesSecondary: List<Int>? = null,
        equipment: List<Int>? = null,
        ordering: String? = null
    ): ExerciseList

    suspend fun getExerciseById(id: Int): Exercise

    suspend fun createExercise(body: ExerciseCreate): Exercise

    suspend fun updateExercise(id: Int, body: ExerciseUpdate): Exercise

    suspend fun deleteExercise(id: Int): Unit
}
