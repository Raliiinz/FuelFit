package com.example.fuelfit.exercise.api.usecase

import com.example.fuelfit.exercise.api.model.ExerciseList
import com.example.fuelfit.model.ResultWrapper

interface GetExercisesUseCase {
    suspend operator fun invoke(
        limit: Int? = null,
        offset: Int? = null,
        categories: List<Int>?
    ): ResultWrapper<ExerciseList>
}