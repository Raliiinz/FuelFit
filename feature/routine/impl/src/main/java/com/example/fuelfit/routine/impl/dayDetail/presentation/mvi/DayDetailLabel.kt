package com.example.fuelfit.routine.impl.dayDetail.presentation.mvi

internal sealed interface DayDetailLabel {
    data class ShowError(val message: String) : DayDetailLabel
    data class ShowSlotDetails(val slotId: Int) : DayDetailLabel
}
