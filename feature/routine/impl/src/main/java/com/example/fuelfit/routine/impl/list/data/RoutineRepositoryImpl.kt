package com.example.fuelfit.routine.impl.list.data

import com.example.fuelfit.routine.api.common.Routine
import com.example.fuelfit.routine.api.list.repository.RoutineRepository
import com.example.fuelfit.routine.impl.common.toDomain
import com.example.fuelfit.routine.impl.list.data.remote.RoutineApiService

class RoutineRepositoryImpl(
    private val api: RoutineApiService
) : RoutineRepository {

    override suspend fun getRoutines(): List<Routine> =
        api.getRoutines().results.map { it.toDomain() }

    override suspend fun getRoutine(id: Int): Routine =
        api.getRoutine(id).toDomain()

    override suspend fun deleteRoutine(id: Int) {
        api.deleteRoutine(id)
    }
}
