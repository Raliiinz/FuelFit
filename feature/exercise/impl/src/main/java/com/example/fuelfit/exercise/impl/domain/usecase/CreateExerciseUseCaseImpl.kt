package com.example.fuelfit.exercise.impl.domain.usecase

import com.example.fuelfit.exercise.api.model.Exercise
import com.example.fuelfit.exercise.api.model.ExerciseCreate
import com.example.fuelfit.exercise.api.repository.ExerciseRepository
import com.example.fuelfit.exercise.api.usecase.CreateExerciseUseCase

class CreateExerciseUseCaseImpl(
    private val repository: ExerciseRepository
) : CreateExerciseUseCase {
    override suspend fun invoke(body: ExerciseCreate): Exercise {
        return repository.createExercise(body)
    }
}
