package com.example.fuelfit.exercise.api.usecase

import com.example.fuelfit.exercise.api.model.ExerciseInfo
import com.example.fuelfit.model.ResultWrapper

interface GetExerciseByIdUseCase {
    suspend operator fun invoke(id: Int): ResultWrapper<ExerciseInfo>
}
