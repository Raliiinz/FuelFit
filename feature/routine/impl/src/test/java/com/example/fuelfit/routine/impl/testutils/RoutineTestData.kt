package com.example.fuelfit.routine.impl.testutils

import com.example.fuelfit.routine.api.common.Routine
import com.example.fuelfit.routine.api.common.RoutineRequest
import java.time.LocalDate
import java.time.OffsetDateTime

object RoutineTestData {

    fun makeRoutineRequest(
        name: String = "My Routine",
        description: String? = "Test routine",
        start: LocalDate = LocalDate.of(2026, 1, 1),
        end: LocalDate = LocalDate.of(2026, 1, 7),
        fitInWeek: Boolean = true,
        isTemplate: Boolean = false,
        isPublic: Boolean = false
    ) = RoutineRequest(
        name = name,
        description = description,
        start = start,
        end = end,
        fitInWeek = fitInWeek,
        isTemplate = isTemplate,
        isPublic = isPublic
    )

    fun makeRoutine(
        id: Int = 1,
        name: String = "My Routine",
        description: String? = "Test routine",
        created: OffsetDateTime = OffsetDateTime.now(),
        start: LocalDate = LocalDate.of(2026, 1, 1),
        end: LocalDate = LocalDate.of(2026, 1, 7),
        fitInWeek: Boolean = true,
        isTemplate: Boolean = false,
        isPublic: Boolean = false
    ) = Routine(
        id = id,
        name = name,
        description = description,
        created = created,
        start = start,
        end = end,
        fitInWeek = fitInWeek,
        isTemplate = isTemplate,
        isPublic = isPublic
    )
}
