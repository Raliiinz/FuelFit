package com.example.fuelfit.routine.impl.dayDetail.presentation.mvi

import com.example.fuelfit.routine.impl.details.presentation.mvi.RoutineDayLabel

internal sealed interface DayDetailLabel {
    data class ShowError(val message: String) : DayDetailLabel
    data class ShowSlotDetails(val slotId: Int) : DayDetailLabel
    object NavigateBack : DayDetailLabel
}
