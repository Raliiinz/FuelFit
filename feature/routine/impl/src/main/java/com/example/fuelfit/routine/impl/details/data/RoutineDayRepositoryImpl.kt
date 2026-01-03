package com.example.fuelfit.routine.impl.details.data

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.network.safeApiCall
import com.example.fuelfit.routine.api.details.model.*
import com.example.fuelfit.routine.api.details.repository.RoutineDayRepository
import com.example.fuelfit.routine.impl.details.data.mapper.RoutineDayMapper
import com.example.fuelfit.routine.impl.details.data.remote.RoutineDayApiService

internal class RoutineDayRepositoryImpl(
    private val api: RoutineDayApiService,
    private val mapper: RoutineDayMapper
) : RoutineDayRepository {

    override suspend fun getDaysByRoutine(routineId: Int): ResultWrapper<List<RoutineDay>> =
        safeApiCall {
            api.getDays(routineId)
                .results
                .map(mapper::fromDto)
                .filter { it.routineId == routineId }
        }

    override suspend fun getDay(id: Int): ResultWrapper<RoutineDay> =
        safeApiCall {
            mapper.fromDto(api.getDay(id))
        }

    override suspend fun createDay(dayRequest: RoutineDayRequest): ResultWrapper<RoutineDay> =
        safeApiCall {
            val dto = mapper.toRequest(dayRequest)
            val createdDto = api.createDay(dto)
            mapper.fromDto(createdDto)
        }

    override suspend fun updateDay(day: RoutineDay): ResultWrapper<RoutineDay> =
        safeApiCall {
            val dto = mapper.toRequest(day)
            val updatedDto = api.updateDay(day.id, dto)
            mapper.fromDto(updatedDto)
        }

    override suspend fun updateDayFromRequest(dayRequest: RoutineDayRequest, id: Int): ResultWrapper<RoutineDay> =
        safeApiCall {
            val dto = mapper.toRequest(dayRequest)
            val updatedDto = api.updateDay(id, dto)
            mapper.fromDto(updatedDto)
        }

    override suspend fun deleteDay(id: Int): ResultWrapper<Unit> =
        safeApiCall {
            api.deleteDay(id)
        }
}
