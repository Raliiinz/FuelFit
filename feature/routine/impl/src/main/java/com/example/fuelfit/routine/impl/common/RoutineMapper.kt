package com.example.fuelfit.routine.impl.common

import com.example.fuelfit.routine.api.common.Routine
import com.example.fuelfit.routine.api.common.RoutineRequest
import com.example.fuelfit.routine.impl.common.dto.RoutineDto
import com.example.fuelfit.routine.impl.common.dto.RoutineRequestDto
import java.time.LocalDate
import java.time.OffsetDateTime

fun RoutineDto.toDomain(): Routine =
    Routine(
        id = id,
        name = name,
        description = description,
        created = OffsetDateTime.parse(created),
        start = LocalDate.parse(start),
        end = LocalDate.parse(end),
        fitInWeek = fitInWeek,
        isTemplate = isTemplate,
        isPublic = isPublic
    )

fun RoutineRequest.toDto() =
    RoutineRequestDto(
        name = name,
        description = description,
        start = start.toString(),
        end = end.toString(),
        fitInWeek = fitInWeek,
        isTemplate = isTemplate,
        isPublic = isPublic
    )