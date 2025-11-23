package com.example.fuelfit.auth.impl.presentation.register.mvi

import com.arkivanov.mvikotlin.core.store.Store

internal interface RegisterStore :
    Store<RegisterIntent, RegisterState, RegisterLabel> {

    interface Factory {
        fun create(): RegisterStore
    }
}
