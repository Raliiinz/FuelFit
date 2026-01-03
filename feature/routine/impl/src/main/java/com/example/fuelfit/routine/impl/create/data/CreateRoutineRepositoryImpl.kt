package com.example.fuelfit.routine.impl.create.data

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.network.safeApiCall
import com.example.fuelfit.routine.api.common.RoutineRequest
import com.example.fuelfit.routine.api.common.Routine
import com.example.fuelfit.routine.api.create.repository.CreateRoutineRepository
import com.example.fuelfit.routine.impl.common.RoutineMapper
import com.example.fuelfit.routine.impl.create.data.remote.CreateRoutineApiService

internal class CreateRoutineRepositoryImpl(
    private val api: CreateRoutineApiService,
    private val mapper: RoutineMapper
) : CreateRoutineRepository {

    override suspend fun createRoutine(request: RoutineRequest): ResultWrapper<Routine> =
        safeApiCall {
            val dto = mapper.toDto(request)
            val createdDto = api.createRoutine(dto)
            mapper.fromDto(createdDto)
        }

    override suspend fun updateRoutine(id: Int, request: RoutineRequest): ResultWrapper<Routine> =
        safeApiCall {
            val dto = mapper.toDto(request)
            val updatedDto = api.updateRoutine(id, dto)
            mapper.fromDto(updatedDto)
        }
}
