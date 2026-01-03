package com.example.fuelfit.routine.impl.common

import com.example.fuelfit.routine.api.common.Routine
import com.example.fuelfit.routine.api.common.RoutineRequest
import com.example.fuelfit.routine.impl.common.dto.RoutineDto
import com.example.fuelfit.routine.impl.common.dto.RoutineRequestDto
import java.time.LocalDate
import java.time.OffsetDateTime

internal class RoutineMapper {

    fun fromDto(dto: RoutineDto): Routine =
        Routine(
            id = dto.id,
            name = dto.name,
            description = dto.description,
            created = OffsetDateTime.parse(dto.created),
            start = LocalDate.parse(dto.start),
            end = LocalDate.parse(dto.end),
            fitInWeek = dto.fitInWeek,
            isTemplate = dto.isTemplate,
            isPublic = dto.isPublic
        )

    fun toDto(request: RoutineRequest): RoutineRequestDto =
        RoutineRequestDto(
            name = request.name,
            description = request.description,
            start = request.start.toString(),
            end = request.end.toString(),
            fitInWeek = request.fitInWeek,
            isTemplate = request.isTemplate,
            isPublic = request.isPublic
        )
}
