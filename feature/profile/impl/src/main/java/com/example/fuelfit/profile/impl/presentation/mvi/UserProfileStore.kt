package com.example.fuelfit.profile.impl.presentation.mvi

import com.arkivanov.mvikotlin.core.store.Store

internal interface UserProfileStore :
    Store<UserProfileIntent, UserProfileState, UserProfileLabel> {

    interface Factory {
        fun create(): UserProfileStore
    }
}
