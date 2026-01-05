package com.example.fuelfit.exercise.impl.presentation.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.example.fuelfit.exercise.impl.presentation.list.mvi.ExercisesIntent
import com.example.fuelfit.exercise.impl.presentation.list.mvi.ExercisesLabel
import com.example.fuelfit.exercise.impl.presentation.list.mvi.ExercisesState
import com.example.fuelfit.exercise.impl.presentation.list.mvi.ExercisesStore
import com.example.fuelfit.exercise.impl.presentation.list.mvi.ExercisesStoreFactory
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
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.example.fuelfit.utils.analytics.AnalyticsTracker
import com.example.fuelfit.utils.analytics.Screen

internal class ExercisesListComponent(
    componentContext: ComponentContext,
    private val onExerciseClicked: (Int) -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val analytics: AnalyticsTracker by inject()
    private val storeFactory: ExercisesStoreFactory by inject()
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val store: ExercisesStore = instanceKeeper.getStore { storeFactory.create() }
    internal val state: Value<ExercisesState> = store.asValue()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbarFlow: SharedFlow<String> = _snackbar

    init {
        analytics.screenOpened(Screen.EXERCISES)

        lifecycle.doOnCreate {
            scope.launch {
                store.labels.collect { label ->
                    when (label) {
                        is ExercisesLabel.ShowError -> _snackbar.emit(label.message)
                        is ExercisesLabel.NavigateToExerciseDetail -> onExerciseClicked(label.id)
                    }
                }
            }
        }

        lifecycle.doOnDestroy { scope.cancel() }
    }

    internal fun onIntent(intent: ExercisesIntent) {
        store.accept(intent)
    }
}
