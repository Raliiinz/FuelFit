package com.example.fuelfit.exercise.impl.presentation.detail

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.example.fuelfit.exercise.impl.presentation.detail.mvi.ExerciseDetailIntent
import com.example.fuelfit.exercise.impl.presentation.detail.mvi.ExerciseDetailLabel
import com.example.fuelfit.exercise.impl.presentation.detail.mvi.ExerciseDetailState
import com.example.fuelfit.exercise.impl.presentation.detail.mvi.ExerciseDetailStore
import com.example.fuelfit.exercise.impl.presentation.detail.mvi.ExerciseDetailStoreFactory
import com.example.fuelfit.utils.asValue
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

internal class ExerciseDetailComponent(
    componentContext: ComponentContext,
    exerciseId: Int,
    val onBack: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val storeFactory: ExerciseDetailStoreFactory by inject()
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val store: ExerciseDetailStore = instanceKeeper.getStore { storeFactory.create(exerciseId) }
    internal val state: Value<ExerciseDetailState> = store.asValue()

    private val _snackbar = MutableSharedFlow<String>()
    val snackbarFlow: SharedFlow<String> = _snackbar

    init {
        lifecycle.doOnCreate {
            scope.launch {
                store.labels.collect { label ->
                    when (label) {
                        is ExerciseDetailLabel.ShowError -> _snackbar.emit(label.message)
                    }
                }
            }
        }

        lifecycle.doOnDestroy { scope.cancel() }
    }

    internal fun onIntent(intent: ExerciseDetailIntent) {
        store.accept(intent)
    }
}
