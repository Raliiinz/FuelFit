package com.example.fuelfit.exercise.impl.domain.usecase

import com.example.fuelfit.exercise.api.model.ExerciseInfo
import com.example.fuelfit.exercise.api.repository.ExerciseRepository
import com.example.fuelfit.exercise.api.usecase.GetExerciseByIdUseCase
import com.example.fuelfit.model.ResultWrapper

internal class GetExerciseByIdUseCaseImpl(
    private val repository: ExerciseRepository
) : GetExerciseByIdUseCase {
    override suspend fun invoke(id: Int): ResultWrapper<ExerciseInfo> {
        return repository.getExerciseById(id)
    }
}
