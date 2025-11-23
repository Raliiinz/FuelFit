package com.example.fuelfit.exercise.impl.domain.usecase

import com.example.fuelfit.exercise.api.model.Exercise
import com.example.fuelfit.exercise.api.model.ExerciseUpdate
import com.example.fuelfit.exercise.api.repository.ExerciseRepository
import com.example.fuelfit.exercise.api.usecase.UpdateExerciseUseCase

class UpdateExerciseUseCaseImpl(
    private val repository: ExerciseRepository
) : UpdateExerciseUseCase {
    override suspend fun invoke(id: Int, body: ExerciseUpdate): Exercise {
        return repository.updateExercise(id, body)
    }
}
