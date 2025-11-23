package com.example.fuelfit.auth.impl.presentation.register.mvi

internal sealed interface RegisterAction {
    data object Init : RegisterAction
}
