package com.example.fuelfit.routine.impl.dayDetail.presentation.mvi

import com.example.fuelfit.routine.api.dayDetail.model.SlotEntryRequest
import com.example.fuelfit.routine.api.dayDetail.model.SlotRequest

internal sealed interface DayDetailIntent {

    object Init : DayDetailIntent
    object Refresh : DayDetailIntent

    data class OpenSearch(val slotId: Int) : DayDetailIntent
    object CloseSearch : DayDetailIntent
    data class SearchExercises(val query: String) : DayDetailIntent
    data class SelectExercise(
        val slotId: Int,
        val exerciseId: Int,
        val exerciseName: String
    ) : DayDetailIntent

    data class CreateSlot(val request: SlotRequest) : DayDetailIntent
    data class UpdateSlot(val id: Int, val request: SlotRequest) : DayDetailIntent
    data class DeleteSlot(val id: Int) : DayDetailIntent
    data class SlotClicked(val id: Int) : DayDetailIntent
    data class CreateEntry(val request: SlotEntryRequest) : DayDetailIntent
    data class UpdateEntry(val id: Int, val request: SlotEntryRequest) : DayDetailIntent
    data class DeleteEntry(val id: Int) : DayDetailIntent
    object BackClicked : DayDetailIntent
}
