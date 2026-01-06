package com.example.fuelfit.routine.impl.dayDetail.presentation.mvi

import com.example.fuelfit.exercise.api.model.ExerciseSearchItem
import com.example.fuelfit.routine.api.dayDetail.model.Slot
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntry

internal data class DayDetailState(
    val dayId: Int,
    val slots: List<Slot> = emptyList(),
    val entriesBySlot: Map<Int, List<SlotEntry>> = emptyMap(),
    val exerciseNamesById: Map<Int, String> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null,

    val searchQuery: String = "",
    val searchResults: List<ExerciseSearchItem> = emptyList(),
    val isSearching: Boolean = false,
    val searchSlotId: Int? = null
)
