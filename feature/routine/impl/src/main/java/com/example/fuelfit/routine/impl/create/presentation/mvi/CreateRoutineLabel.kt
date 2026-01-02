package com.example.fuelfit.routine.impl.create.presentation.mvi

internal sealed interface CreateRoutineLabel {
    object Close : CreateRoutineLabel
    data class ShowError(val message: String) : CreateRoutineLabel
}
