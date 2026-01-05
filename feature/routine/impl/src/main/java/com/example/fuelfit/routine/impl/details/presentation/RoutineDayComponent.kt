package com.example.fuelfit.routine.impl.details.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.example.fuelfit.routine.impl.details.presentation.mvi.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.example.fuelfit.utils.analytics.AnalyticsTracker
import com.example.fuelfit.utils.analytics.Screen
import com.example.fuelfit.utils.mvi.asValue

internal class RoutineDayComponent(
    componentContext: ComponentContext,
    private val routineId: Int,
    private val onDayClicked: (Int) -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val analytics: AnalyticsTracker by inject()
    private val factory: RoutineDayStoreFactory by inject()
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val store: RoutineDayStore = instanceKeeper.getStore { factory.create(routineId) }

    internal val state: Value<RoutineDayState> = store.asValue()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbarFlow: SharedFlow<String> = _snackbar

    private val _toast = MutableSharedFlow<String>()
    val toastFlow: SharedFlow<String> = _toast

    init {
        analytics.screenOpened(Screen.ROUTINE_DETAILS)

        lifecycle.doOnCreate {
            scope.launch {
                store.labels.collect {
                    when (it) {
                        is RoutineDayLabel.ShowError -> _snackbar.emit(it.message)
                        is RoutineDayLabel.NavigateToDayDetail -> onDayClicked(it.id)
                        is RoutineDayLabel.ShowToast ->  _toast.emit(it.message)
                    }
                }
            }
        }
        lifecycle.doOnDestroy { scope.cancel() }
    }

    internal fun onIntent(intent: RoutineDayIntent) {
        store.accept(intent)
//        when (intent) {
//
//            is RoutineDayIntent.CreateDay -> {
//                val dayWithRoutineId = intent.day.copy(routineId = routineId)
//                store.accept(RoutineDayIntent.CreateDay(dayWithRoutineId))
//            }
//            else ->
//        }
    }
}
