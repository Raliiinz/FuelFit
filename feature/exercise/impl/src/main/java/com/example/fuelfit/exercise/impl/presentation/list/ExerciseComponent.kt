package com.example.fuelfit.exercise.impl.presentation.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.example.fuelfit.exercise.impl.presentation.list.mvi.ExerciseIntent
import com.example.fuelfit.exercise.impl.presentation.list.mvi.ExerciseLabel
import com.example.fuelfit.exercise.impl.presentation.list.mvi.ExerciseState
import com.example.fuelfit.exercise.impl.presentation.list.mvi.ExerciseStore
import com.example.fuelfit.exercise.impl.presentation.list.mvi.ExerciseStoreFactory
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
import com.arkivanov.mvikotlin.extensions.coroutines.labels

class ExerciseComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, KoinComponent {

    private val storeFactory: ExerciseStoreFactory by inject()
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val store: ExerciseStore = instanceKeeper.getStore { storeFactory.create() }

    internal val state: Value<ExerciseState> = store.asValue()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbarFlow: SharedFlow<String> = _snackbar

    init {
        scope.launch {
            store.labels.collect { label ->
                when (label) {
                    is ExerciseLabel.ShowError -> _snackbar.emit(label.message)
                }
            }
        }
    }

    internal fun onIntent(intent: ExerciseIntent) {
        store.accept(intent)
    }

    fun onDestroy() {
        scope.cancel()
    }
}
