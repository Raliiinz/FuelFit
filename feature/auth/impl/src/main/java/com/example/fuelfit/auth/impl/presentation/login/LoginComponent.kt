package com.example.fuelfit.auth.impl.presentation.login

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.example.fuelfit.auth.impl.presentation.login.mvi.LoginIntent
import com.example.fuelfit.auth.impl.presentation.login.mvi.LoginLabel
import com.example.fuelfit.auth.impl.presentation.login.mvi.LoginState
import com.example.fuelfit.auth.impl.presentation.login.mvi.LoginStore
import com.example.fuelfit.auth.impl.presentation.login.mvi.LoginStoreFactory
import com.example.fuelfit.utils.asValue
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.koin.core.component.KoinComponent

class LoginComponent(
    componentContext: ComponentContext,
    private val onNavigateMain: () -> Unit,
    private val onNavigateRegister: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val storeFactory: LoginStoreFactory by inject()

    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val store: LoginStore =
        instanceKeeper.getStore { storeFactory.create() }

    internal val state: Value<LoginState> = store.asValue()

    init {
        lifecycle.doOnCreate {
            scope.launch {
                store.labels.collect { label ->
                    when (label) {
                        LoginLabel.NavigateToMain -> onNavigateMain()
                        is LoginLabel.ShowError -> _snackbar.emit(label.message)
                        LoginLabel.NavigateToRegister -> onNavigateRegister()
                    }
                }
            }
        }

        lifecycle.doOnDestroy { scope.cancel() }
    }

    private val _snackbar = MutableSharedFlow<String>()
    val snackbarFlow: SharedFlow<String> = _snackbar

    internal fun onIntent(intent: LoginIntent) {
        store.accept(intent)
    }
}

