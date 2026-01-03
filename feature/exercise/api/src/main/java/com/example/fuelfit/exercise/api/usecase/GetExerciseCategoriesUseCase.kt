package com.example.fuelfit.exercise.api.usecase

import com.example.fuelfit.exercise.api.model.ExerciseCategory
import com.example.fuelfit.model.ResultWrapper

interface GetExerciseCategoriesUseCase {
    suspend operator fun invoke(limit: Int? = null, offset: Int? = null, name: String? = null, ordering: String? = null): ResultWrapper<List<ExerciseCategory>>
}
