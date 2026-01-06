package com.example.fuelfit.routine.api.details.model

data class RoutineDayRequest(
    val routineId: Int,
    val order: Int,
    val name: String,
    val description: String?,
    val isRest: Boolean,
    val needLogsToAdvance: Boolean = false,
    val type: RoutineDayType = RoutineDayType.CUSTOM,
    val config: String? = null
)
