package com.example.fuelfit.routine.api.create.model

import java.time.LocalDate

data class RoutineRequest(
    val name: String,
    val description: String?,
    val start: LocalDate,
    val end: LocalDate,
    val fitInWeek: Boolean,
    val isTemplate: Boolean,
    val isPublic: Boolean
)
