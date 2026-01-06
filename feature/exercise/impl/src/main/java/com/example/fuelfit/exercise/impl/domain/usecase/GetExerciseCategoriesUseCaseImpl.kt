package com.example.fuelfit.exercise.impl.domain.usecase

import com.example.fuelfit.exercise.api.model.ExerciseCategory
import com.example.fuelfit.exercise.api.repository.ExerciseRepository
import com.example.fuelfit.exercise.api.usecase.GetExerciseCategoriesUseCase
import com.example.fuelfit.model.ResultWrapper

internal class GetExerciseCategoriesUseCaseImpl(
    private val repository: ExerciseRepository
) : GetExerciseCategoriesUseCase {
    override suspend fun invoke(
        limit: Int?,
        offset: Int?,
        name: String?,
        ordering: String?
    ): ResultWrapper<List<ExerciseCategory>> {
        return repository.getExerciseCategories(limit, offset, name, ordering)
    }
}
