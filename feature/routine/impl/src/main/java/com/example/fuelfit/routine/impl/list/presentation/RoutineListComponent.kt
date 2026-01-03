package com.example.fuelfit.routine.impl.list.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.example.fuelfit.routine.impl.list.presentation.mvi.RoutineIntent
import com.example.fuelfit.routine.impl.list.presentation.mvi.RoutineLabel
import com.example.fuelfit.routine.impl.list.presentation.mvi.RoutineState
import com.example.fuelfit.routine.impl.list.presentation.mvi.RoutineStore
import com.example.fuelfit.routine.impl.list.presentation.mvi.RoutineStoreFactory
import com.example.fuelfit.utils.asValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class RoutineListComponent(
    componentContext: ComponentContext,
    private val onRoutineClick: (Int) -> Unit,
    private val onCreateRoutineClicked: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val factory: RoutineStoreFactory by inject()
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val store: RoutineStore = instanceKeeper.getStore { factory.create() }

    internal val state: Value<RoutineState> = store.asValue()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbarFlow: SharedFlow<String> = _snackbar

    init {
        lifecycle.doOnCreate {
            scope.launch {
                store.labels.collect {
                    when (it) {
                        is RoutineLabel.ShowError -> _snackbar.emit(it.message)
                        is RoutineLabel.NavigateToRoutine -> onRoutineClick(it.id)
                        RoutineLabel.NavigateToCreateRoutine -> onCreateRoutineClicked()
                    }
                }
            }
        }
        lifecycle.doOnDestroy { scope.cancel() }
    }

    internal fun onIntent(intent: RoutineIntent) {
        store.accept(intent)
    }
}
