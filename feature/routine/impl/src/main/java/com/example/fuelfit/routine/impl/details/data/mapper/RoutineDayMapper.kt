package com.example.fuelfit.routine.impl.details.data.mapper

import com.example.fuelfit.routine.api.details.model.*
import com.example.fuelfit.routine.impl.details.data.remote.dto.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

internal class RoutineDayMapper {

    fun fromDto(dto: RoutineDayDto): RoutineDay =
        RoutineDay(
            id = dto.id,
            routineId = dto.routine,
            order = dto.order,
            name = dto.name,
            description = dto.description,
            isRest = dto.isRest,
            needLogsToAdvance = dto.needLogsToAdvance,
            type = dto.type.toDomain(),
            config = dto.config?.toString()
        )

    fun toRequest(day: RoutineDay): RoutineDayRequestDto =
        RoutineDayRequestDto(
            routine = day.routineId,
            order = day.order,
            name = day.name,
            description = day.description,
            isRest = day.isRest,
            needLogsToAdvance = day.needLogsToAdvance,
            type = day.type.toDto()
        )

    fun toRequest(dayRequest: RoutineDayRequest): RoutineDayRequestDto =
        RoutineDayRequestDto(
            routine = dayRequest.routineId,
            order = dayRequest.order,
            name = dayRequest.name,
            description = dayRequest.description,
            isRest = dayRequest.isRest,
            needLogsToAdvance = dayRequest.needLogsToAdvance,
            type = dayRequest.type.toDto(),
            config = dayRequest.config?.let { Json.parseToJsonElement(it).jsonObject }
        )

    private fun RoutineDayTypeDto.toDomain(): RoutineDayType =
        RoutineDayType.valueOf(name)

    private fun RoutineDayType.toDto(): RoutineDayTypeDto =
        RoutineDayTypeDto.valueOf(name)
}
