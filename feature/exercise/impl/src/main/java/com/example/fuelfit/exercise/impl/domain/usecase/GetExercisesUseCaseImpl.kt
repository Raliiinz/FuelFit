package com.example.fuelfit.exercise.impl.domain.usecase

import com.example.fuelfit.exercise.api.model.ExerciseList
import com.example.fuelfit.exercise.api.repository.ExerciseRepository
import com.example.fuelfit.exercise.api.usecase.GetExercisesUseCase
import com.example.fuelfit.model.ResultWrapper

internal class GetExercisesUseCaseImpl(
    private val repository: ExerciseRepository
) : GetExercisesUseCase {
    override suspend fun invoke(limit: Int?, offset: Int?, categories: List<Int>?): ResultWrapper<ExerciseList> {
        return repository.getExercises(
            limit = limit,
            offset = offset,
            categories = categories
        )
    }
}