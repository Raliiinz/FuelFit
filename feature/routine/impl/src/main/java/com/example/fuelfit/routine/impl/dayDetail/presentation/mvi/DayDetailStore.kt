package com.example.fuelfit.routine.impl.dayDetail.presentation.mvi

import com.arkivanov.mvikotlin.core.store.Store

internal interface DayDetailStore :
    Store<DayDetailIntent, DayDetailState, DayDetailLabel> {

    interface Factory {
        fun create(dayId: Int): DayDetailStore
    }
}
