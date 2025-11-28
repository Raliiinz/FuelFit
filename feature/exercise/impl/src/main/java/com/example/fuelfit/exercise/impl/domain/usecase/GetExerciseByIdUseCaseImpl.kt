package com.example.fuelfit.exercise.impl.domain.usecase

import com.example.fuelfit.exercise.api.model.Exercise
import com.example.fuelfit.exercise.api.repository.ExerciseRepository
import com.example.fuelfit.exercise.api.usecase.GetExerciseByIdUseCase

class GetExerciseByIdUseCaseImpl(
    private val repository: ExerciseRepository
) : GetExerciseByIdUseCase {
    override suspend fun invoke(id: Int): Exercise {
//        return repository.getExerciseById(id)
        TODO()
    }
}