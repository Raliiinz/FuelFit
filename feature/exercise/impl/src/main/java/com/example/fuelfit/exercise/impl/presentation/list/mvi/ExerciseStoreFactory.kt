package com.example.fuelfit.exercise.impl.presentation.list.mvi

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.exercise.api.usecase.GetExercisesUseCase
import kotlinx.coroutines.launch

internal class ExerciseStoreFactory(
    private val storeFactory: StoreFactory,
    private val getExercisesUseCase: GetExercisesUseCase
) : ExerciseStore.Factory {

    override fun create(): ExerciseStore =
        object : ExerciseStore,
            Store<ExerciseIntent, ExerciseState, ExerciseLabel> by storeFactory.create(
                name = "ExerciseStore",
                initialState = ExerciseState(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = { Executor() },
                reducer = ReducerImpl
            ) {}

    private inner class Executor :
        CoroutineExecutor<ExerciseIntent, Unit, ExerciseState, ExerciseMsg, ExerciseLabel>() {

        override fun executeAction(action: Unit) {
            loadExercises()
        }

        override fun executeIntent(intent: ExerciseIntent) {
            when (intent) {
                is ExerciseIntent.SearchQueryChanged -> dispatch(ExerciseMsg.SetQuery(intent.query))
                ExerciseIntent.Refresh -> loadExercises()
            }
        }

        private fun loadExercises() {
            dispatch(ExerciseMsg.Loading)
            scope.launch {
                try {
                    val exercises = getExercisesUseCase(null, null)
                    dispatch(ExerciseMsg.SetExercises(exercises))
                } catch (e: Exception) {
                    dispatch(ExerciseMsg.Error(e.message ?: "Ошибка загрузки"))
                    publish(ExerciseLabel.ShowError(e.message ?: "Ошибка загрузки"))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<ExerciseState, ExerciseMsg> {
        override fun ExerciseState.reduce(msg: ExerciseMsg): ExerciseState =
            when (msg) {
                is ExerciseMsg.SetExercises -> copy(exercises = msg.exercises, isLoading = false, error = null)
                is ExerciseMsg.SetQuery -> copy(query = msg.query)
                ExerciseMsg.Loading -> copy(isLoading = true, error = null)
                is ExerciseMsg.Error -> copy(isLoading = false, error = msg.message)
            }
    }
}
