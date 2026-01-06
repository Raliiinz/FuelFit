package com.example.fuelfit.routine.impl.list.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.example.fuelfit.routine.impl.list.presentation.mvi.RoutinesIntent
import com.example.fuelfit.routine.impl.list.presentation.mvi.RoutinesLabel
import com.example.fuelfit.routine.impl.list.presentation.mvi.RoutinesState
import com.example.fuelfit.routine.impl.list.presentation.mvi.RoutinesStore
import com.example.fuelfit.routine.impl.list.presentation.mvi.RoutinesStoreFactory
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

internal class RoutinesListComponent(
    componentContext: ComponentContext,
    private val onRoutineClick: (Int) -> Unit,
    private val onCreateRoutineClicked: () -> Unit,
    private val onEditRoutineClicked: (Int) -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val analytics: AnalyticsTracker by inject()
    private val factory: RoutinesStoreFactory by inject()
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val store: RoutinesStore = instanceKeeper.getStore { factory.create() }

    internal val state: Value<RoutinesState> = store.asValue()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbarFlow: SharedFlow<String> = _snackbar

    init {
        analytics.screenOpened(Screen.ROUTINES_LIST)

        lifecycle.doOnCreate {
            scope.launch {
                store.labels.collect {
                    when (it) {
                        is RoutinesLabel.ShowError -> _snackbar.emit(it.message)
                        is RoutinesLabel.NavigateToRoutine -> onRoutineClick(it.id)
                        RoutinesLabel.NavigateToCreateRoutine -> onCreateRoutineClicked()
                        is RoutinesLabel.NavigateToEditRoutine -> onEditRoutineClicked(it.id)
                    }
                }
            }
        }
        lifecycle.doOnDestroy { scope.cancel() }
    }

    internal fun onIntent(intent: RoutinesIntent) {
        store.accept(intent)
    }
}
