package com.example.fuelfit.routine.api.details.model

data class RoutineDayRequest(
    val routineId: Int,
    val order: Int,
    val name: String,
    val description: String?,
    val isRest: Boolean,
    val needLogsToAdvance: Boolean,
    val type: RoutineDayType,
    val config: String?
)
