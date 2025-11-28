package com.example.fuelfit.exercise.api.usecase

import com.example.fuelfit.exercise.api.model.ExerciseCategory

interface GetExerciseCategoriesUseCase {
    suspend operator fun invoke(limit: Int? = null, offset: Int? = null, name: String? = null, ordering: String? = null): List<ExerciseCategory>
}
