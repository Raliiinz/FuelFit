package com.example.fuelfit.exercise.api.usecase

import com.example.fuelfit.exercise.api.model.ExerciseList

interface GetExercisesUseCase {
    suspend operator fun invoke(
        limit: Int? = null,
        offset: Int? = null
    ): ExerciseList
}