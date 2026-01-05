package com.example.fuelfit.auth.impl.presentation.register

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.example.fuelfit.auth.impl.presentation.register.mvi.*
import com.example.fuelfit.utils.mvi.asValue
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.example.fuelfit.utils.analytics.AnalyticsTracker
import com.example.fuelfit.utils.analytics.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.koin.core.component.KoinComponent

class RegisterComponent(
    componentContext: ComponentContext,
    private val onNavigateMain: () -> Unit,
    private val onNavigateLogin: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val analytics: AnalyticsTracker by inject()
    private val storeFactory: RegisterStoreFactory by inject()

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val store: RegisterStore =
        instanceKeeper.getStore { storeFactory.create() }

    internal val state: Value<RegisterState> = store.asValue()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbarFlow: SharedFlow<String> = _snackbar

    init {
        analytics.screenOpened(Screen.REGISTER)

        lifecycle.doOnCreate {
            scope.launch {
                store.labels.collect { label ->
                    when (label) {
                        RegisterLabel.NavigateToMain -> onNavigateMain()
                        is RegisterLabel.ShowError -> _snackbar.emit(label.message)
                        RegisterLabel.NavigateToLogin -> onNavigateLogin()
                    }
                }
            }
        }

        lifecycle.doOnDestroy { scope.cancel() }
    }

    internal fun onIntent(intent: RegisterIntent) {
        store.accept(intent)
    }
}

