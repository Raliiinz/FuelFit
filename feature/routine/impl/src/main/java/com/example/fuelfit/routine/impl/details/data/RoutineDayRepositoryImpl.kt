package com.example.fuelfit.routine.impl.details.data

import com.example.fuelfit.routine.api.details.model.RoutineDay
import com.example.fuelfit.routine.api.details.model.RoutineDayRequest
import com.example.fuelfit.routine.api.details.repository.RoutineDayRepository
import com.example.fuelfit.routine.impl.details.data.mapper.RoutineDayMapper
import com.example.fuelfit.routine.impl.details.data.remote.RoutineDayApiService

class RoutineDayRepositoryImpl(
    private val api: RoutineDayApiService
) : RoutineDayRepository {

    override suspend fun getDaysByRoutine(routineId: Int): List<RoutineDay> =
        api.getDays(routineId = routineId)
            .results
            .map(RoutineDayMapper::fromDto)

    override suspend fun getDay(id: Int): RoutineDay =
        RoutineDayMapper.fromDto(api.getDay(id))

    override suspend fun createDay(dayRequest: RoutineDayRequest): RoutineDay {
        val dto = RoutineDayMapper.toRequest(dayRequest)
        val createdDto = api.createDay(dto)
        return RoutineDayMapper.fromDto(createdDto)
    }

    override suspend fun updateDay(day: RoutineDay): RoutineDay {
        val dto = RoutineDayMapper.toRequest(day)
        val updatedDto = api.updateDay(day.id, dto)
        return RoutineDayMapper.fromDto(updatedDto)
    }

    override suspend fun deleteDay(id: Int) {
        api.deleteDay(id)
    }
}
