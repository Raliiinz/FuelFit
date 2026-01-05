package com.example.fuelfit.routine.impl.create.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.example.fuelfit.routine.impl.create.presentation.mvi.CreateRoutineIntent
import com.example.fuelfit.routine.impl.create.presentation.mvi.CreateRoutineLabel
import com.example.fuelfit.routine.impl.create.presentation.mvi.CreateRoutineState
import com.example.fuelfit.routine.impl.create.presentation.mvi.CreateRoutineStore
import com.example.fuelfit.routine.impl.create.presentation.mvi.CreateRoutineStoreFactory
import com.example.fuelfit.utils.analytics.AnalyticsTracker
import com.example.fuelfit.utils.analytics.Screen
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

internal class CreateRoutineComponent(
    componentContext: ComponentContext,
    private val onClose: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val analytics: AnalyticsTracker by inject()
    private val factory: CreateRoutineStoreFactory by inject()
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val store: CreateRoutineStore =
        instanceKeeper.getStore { factory.create() }

    internal val state: Value<CreateRoutineState> = store.asValue()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbarFlow: SharedFlow<String> = _snackbar

    init {
        analytics.screenOpened(Screen.CREATE_ROUTINE)

        lifecycle.doOnCreate {
            scope.launch {
                store.labels.collect {
                    when (it) {
                        CreateRoutineLabel.Close -> onClose()
                        is CreateRoutineLabel.ShowError -> _snackbar.emit(it.message)
                    }
                }
            }
        }
        lifecycle.doOnDestroy { scope.cancel() }
    }

    internal fun onIntent(intent: CreateRoutineIntent) {
        store.accept(intent)
    }
}
