package com.example.fuelfit.auth.impl.presentation.login.mvi

import com.arkivanov.mvikotlin.core.store.Store

internal interface LoginStore :
    Store<LoginIntent, LoginState, LoginLabel> {

    interface Factory {
        fun create(): LoginStore
    }
}
