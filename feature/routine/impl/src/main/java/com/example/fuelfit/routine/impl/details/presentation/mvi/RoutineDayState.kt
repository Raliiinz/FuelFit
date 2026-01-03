package com.example.fuelfit.routine.impl.details.presentation.mvi

import com.example.fuelfit.routine.api.details.model.RoutineDay

internal data class RoutineDayState(
    val routineId: Int,
    val days: List<RoutineDay> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
