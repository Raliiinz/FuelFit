package com.example.fuelfit.routine.api.common

import java.time.LocalDate
import java.time.OffsetDateTime

data class Routine(
    val id: Int,
    val name: String,
    val description: String?,
    val created: OffsetDateTime,
    val start: LocalDate,
    val end: LocalDate,
    val fitInWeek: Boolean,
    val isTemplate: Boolean,
    val isPublic: Boolean
)
