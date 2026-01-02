package com.example.fuelfit.routine.impl.create.presentation.mvi

import java.time.LocalDate

internal data class CreateRoutineState(
    val name: String = "",
    val description: String = "",
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now(),
    val fitInWeek: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null
) {
    val canSave: Boolean
        get() = name.isNotBlank() && !isSaving
}
