package com.example.fuelfit.routine.impl.dayDetail.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.example.fuelfit.routine.impl.dayDetail.presentation.mvi.DayDetailIntent
import com.example.fuelfit.routine.impl.dayDetail.presentation.mvi.DayDetailLabel
import com.example.fuelfit.routine.impl.dayDetail.presentation.mvi.DayDetailState
import com.example.fuelfit.routine.impl.dayDetail.presentation.mvi.DayDetailStore
import com.example.fuelfit.routine.impl.dayDetail.presentation.mvi.DayDetailStoreFactory
import com.example.fuelfit.utils.analytics.AnalyticsTracker
import com.example.fuelfit.utils.analytics.Screen
import com.example.fuelfit.utils.mvi.asValue
import kotlin.getValue

internal class DayDetailComponent(
    componentContext: ComponentContext,
    private val dayId: Int,
    private val onSlotClicked: (Int) -> Unit,
    private val onBack: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val analytics: AnalyticsTracker by inject()
    private val factory: DayDetailStoreFactory by inject()
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val store: DayDetailStore = instanceKeeper.getStore { factory.create(dayId) }

    internal val state: Value<DayDetailState> = store.asValue()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbarFlow: SharedFlow<String> = _snackbar

    init {
        analytics.screenOpened(Screen.ROUTINE_DAY_DETAIL)

        lifecycle.doOnCreate {
            scope.launch {
                store.labels.collect {
                    when (it) {
                        is DayDetailLabel.ShowError ->  _snackbar.emit(it.message)
                        is DayDetailLabel.ShowSlotDetails -> {
                            // Тут можно открыть детали слота или прокрутить LazyColumn
                            println("Slot clicked: ${it.slotId}")
                        }
                        DayDetailLabel.NavigateBack -> onBack()
                    }
                }
            }
        }
        lifecycle.doOnDestroy { scope.cancel() }
    }

    internal fun onIntent(intent: DayDetailIntent) {
        store.accept(intent)
    }
}
