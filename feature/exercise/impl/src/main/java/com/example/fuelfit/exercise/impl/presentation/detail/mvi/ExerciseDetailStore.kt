package com.example.fuelfit.exercise.impl.presentation.detail.mvi

import com.arkivanov.mvikotlin.core.store.Store

internal interface ExerciseDetailStore :
    Store<ExerciseDetailIntent, ExerciseDetailState, ExerciseDetailLabel> {

    interface Factory {
        fun create(exerciseId: Int): ExerciseDetailStore
    }
}
