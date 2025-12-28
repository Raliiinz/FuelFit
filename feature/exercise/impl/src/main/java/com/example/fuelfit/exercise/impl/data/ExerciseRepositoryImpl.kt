package com.example.fuelfit.exercise.impl.data

import com.example.fuelfit.exercise.api.model.*
import com.example.fuelfit.exercise.api.repository.ExerciseRepository
import com.example.fuelfit.exercise.impl.data.mapper.toDomain
import com.example.fuelfit.exercise.impl.data.remote.ExerciseApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExerciseRepositoryImpl(
    private val api: ExerciseApiService
) : ExerciseRepository {

    override suspend fun getExercises(
        limit: Int?,
        offset: Int?,
        categories: List<Int>?,
        muscles: List<Int>?,
        musclesSecondary: List<Int>?,
        equipment: List<Int>?,
        ordering: String?
    ): ExerciseList = withContext(Dispatchers.IO) {
        api.getExercisesInfo(
            limit = limit,
            offset = offset,
            categoryIn = categories,
            muscles = muscles,
            musclesSecondary = musclesSecondary,
            equipment = equipment,
            ordering = ordering,
        ).toDomain()
    }

    override suspend fun getExerciseById(id: Int): ExerciseInfo =
        withContext(Dispatchers.IO) {
            api.getExerciseInfoById(id).toDomain()
        }

    override suspend fun getExerciseCategories(
        limit: Int?,
        offset: Int?,
        name: String?,
        ordering: String?
    ): List<ExerciseCategory> = withContext(Dispatchers.IO) {
        api.getExerciseCategories(
            limit = limit,
            offset = offset,
            name = name,
            ordering = ordering
        ).results.map { it.toDomain() }
    }
}
