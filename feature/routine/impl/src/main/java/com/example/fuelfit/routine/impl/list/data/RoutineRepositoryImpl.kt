package com.example.fuelfit.routine.impl.list.data

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.network.safeApiCall
import com.example.fuelfit.routine.api.common.Routine
import com.example.fuelfit.routine.api.list.repository.RoutineRepository
import com.example.fuelfit.routine.impl.common.RoutineMapper
import com.example.fuelfit.routine.impl.list.data.remote.RoutineApiService

internal class RoutineRepositoryImpl(
    private val api: RoutineApiService,
    private val mapper: RoutineMapper
) : RoutineRepository {

    override suspend fun getRoutines(): ResultWrapper<List<Routine>> =
        safeApiCall {
            api.getRoutines().results.map(mapper::fromDto)
        }

    override suspend fun getRoutine(id: Int): ResultWrapper<Routine> =
        safeApiCall {
            mapper.fromDto(api.getRoutine(id))
        }

    override suspend fun deleteRoutine(id: Int): ResultWrapper<Unit> =
        safeApiCall {
            api.deleteRoutine(id)
        }
}
