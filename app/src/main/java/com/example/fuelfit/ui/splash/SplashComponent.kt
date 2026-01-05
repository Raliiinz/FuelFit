package com.example.fuelfit.ui.splash

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.example.fuelfit.ui.splash.mvi.SplashLabel
import com.example.fuelfit.ui.splash.mvi.SplashState
import com.example.fuelfit.ui.splash.mvi.SplashStore
import com.example.fuelfit.ui.splash.mvi.SplashStoreFactory
import com.example.fuelfit.utils.mvi.asValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SplashComponent(
    componentContext: ComponentContext,
    private val onNavigateMain: () -> Unit,
    private val onNavigateLogin: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val factory: SplashStoreFactory by inject()

    private val store: SplashStore by lazy {
        instanceKeeper.getStore {
            factory.create()
        }
    }

    val state: Value<SplashState> = store.asValue()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbarFlow: SharedFlow<String> = _snackbar

    init {
        lifecycle.doOnCreate {
            scope.launch {
                store.labels.collect { label ->
                    when (label) {
                        SplashLabel.NavigateToMain -> onNavigateMain()
                        SplashLabel.NavigateToLogin -> onNavigateLogin()
                        is SplashLabel.ShowError -> _snackbar.emit(label.message)
                    }
                }
            }
        }

        lifecycle.doOnDestroy {
            scope.cancel()
        }
    }
}

