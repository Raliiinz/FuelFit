package com.example.fuelfit.routine.api.details.model

data class RoutineDay(
    val id: Int,
    val routineId: Int,
    val order: Int,
    val name: String,
    val description: String?,
    val isRest: Boolean,
    val needLogsToAdvance: Boolean,
    val type: RoutineDayType,
    val config: String?
)
