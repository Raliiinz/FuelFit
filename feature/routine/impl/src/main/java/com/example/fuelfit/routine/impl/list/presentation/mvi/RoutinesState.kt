package com.example.fuelfit.routine.impl.list.presentation.mvi

import com.example.fuelfit.routine.api.common.Routine

internal data class RoutinesState(
    val routines: List<Routine> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
