package com.example.fuelfit.exercise.impl.domain.usecase

import com.example.fuelfit.exercise.api.model.ExerciseSearchItem
import com.example.fuelfit.exercise.api.repository.ExerciseRepository
import com.example.fuelfit.exercise.api.usecase.SearchExercisesUseCase
import com.example.fuelfit.model.ResultWrapper

internal class SearchExercisesUseCaseImpl(
    private val repository: ExerciseRepository
) : SearchExercisesUseCase {
    override suspend fun invoke(
        term: String,
        language: String
    ): ResultWrapper<List<ExerciseSearchItem>> {
        return repository.searchExercises(term, language)
    }
}
