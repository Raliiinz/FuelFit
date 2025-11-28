package com.example.fuelfit.exercise.impl.data

import com.example.fuelfit.exercise.api.model.*
import com.example.fuelfit.exercise.api.repository.ExerciseRepository
import com.example.fuelfit.exercise.impl.data.mapper.toDomain
import com.example.fuelfit.exercise.impl.data.mapper.toDto
import com.example.fuelfit.exercise.impl.data.remote.ExerciseApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExerciseRepositoryImpl(
    private val api: ExerciseApiService
) : ExerciseRepository {

    override suspend fun getExercises(
        limit: Int?,
        offset: Int?,
        category: Int?,
        muscles: List<Int>?,
        musclesSecondary: List<Int>?,
        equipment: List<Int>?,
        ordering: String?
    ): ExerciseList = withContext(Dispatchers.IO) {
        api.getExercisesInfo(
            limit = limit,
            offset = offset,
            category = category,
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
        api.getExerciseCategories(limit, offset, name, ordering).map { it.toDomain() }
    }


//    override suspend fun createExercise(body: ExerciseCreate): Exercise =
//        withContext(Dispatchers.IO) {
//            api.createExercise(body.toDto()).toDomain()
//        }
//
//    override suspend fun updateExercise(id: Int, body: ExerciseUpdate): Exercise =
//        withContext(Dispatchers.IO) {
//            api.updateExercise(id, body.toDto()).toDomain()
//        }

    override suspend fun deleteExercise(id: Int): Unit =
        withContext(Dispatchers.IO) {
            api.deleteExercise(id)
        }
}
