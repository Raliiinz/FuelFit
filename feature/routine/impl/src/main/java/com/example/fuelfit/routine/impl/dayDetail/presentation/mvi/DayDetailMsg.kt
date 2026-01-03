package com.example.fuelfit.routine.impl.dayDetail.presentation.mvi

import com.example.fuelfit.exercise.api.model.ExerciseSearchItem
import com.example.fuelfit.routine.api.dayDetail.model.*

internal sealed interface DayDetailMsg {

    object Loading : DayDetailMsg

    data class SetSlots(
        val slots: List<Slot>,
        val entries: Map<Int, List<SlotEntry>>
    ) : DayDetailMsg

    data class SlotCreated(val slot: Slot) : DayDetailMsg
    data class SlotUpdated(val slot: Slot) : DayDetailMsg
    data class SlotDeleted(val id: Int) : DayDetailMsg

    data class EntryCreated(val entry: SlotEntry) : DayDetailMsg
    data class EntryUpdated(val entry: SlotEntry) : DayDetailMsg
    data class EntryDeleted(val id: Int) : DayDetailMsg

    data class Error(val message: String) : DayDetailMsg

    data class SearchOpened(val slotId: Int) : DayDetailMsg
    object SearchClosed : DayDetailMsg

    data class SearchStarted(val query: String) : DayDetailMsg
    data class SearchSuccess(val result: List<ExerciseSearchItem>) : DayDetailMsg
}
