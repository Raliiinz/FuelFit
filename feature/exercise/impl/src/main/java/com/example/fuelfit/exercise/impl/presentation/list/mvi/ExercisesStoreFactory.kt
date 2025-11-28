package com.example.fuelfit.exercise.impl.presentation.list.mvi

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.exercise.api.usecase.GetExerciseCategoriesUseCase
import com.example.fuelfit.exercise.api.usecase.GetExercisesUseCase
import kotlinx.coroutines.launch

internal class ExercisesStoreFactory(
    private val storeFactory: StoreFactory,
    private val getExercisesUseCase: GetExercisesUseCase,
    private val getExerciseCategoriesUseCase: GetExerciseCategoriesUseCase
) : ExercisesStore.Factory {

    override fun create(): ExercisesStore =
        object : ExercisesStore,
            Store<ExercisesIntent, ExercisesState, ExercisesLabel> by storeFactory.create(
                name = "ExerciseStore",
                initialState = ExercisesState(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = { Executor() },
                reducer = ReducerImpl
            ) {}

    private inner class Executor :
        CoroutineExecutor<ExercisesIntent, Unit, ExercisesState, ExercisesMsg, ExercisesLabel>() {

        override fun executeAction(action: Unit) {
            loadExercises()
        }

        override fun executeIntent(intent: ExercisesIntent) {
            when (intent) {
                is ExercisesIntent.SearchQueryChanged -> dispatch(ExercisesMsg.SetQuery(intent.query))
                ExercisesIntent.Refresh -> loadExercises()
                ExercisesIntent.LoadCategories -> loadCategories()
                is ExercisesIntent.CategoryToggled ->  toggleCategory(intent.categoryId, intent.isChecked)
            }
        }

        private fun loadExercises() {
            dispatch(ExercisesMsg.Loading)
            scope.launch {
                try {
                    val exercises = getExercisesUseCase(
                        limit = 50,
                        offset = 0,
                        categories = state().selectedCategories.toList()
                    )
                    dispatch(ExercisesMsg.SetExercises(exercises))
                } catch (e: Exception) {
                    dispatch(ExercisesMsg.Error(e.message ?: "Ошибка загрузки"))
                    publish(ExercisesLabel.ShowError(e.message ?: "Ошибка загрузки"))
                }
            }
        }

        private fun loadCategories() {
            scope.launch {
                try {
                    val categories = getExerciseCategoriesUseCase()
                    dispatch(ExercisesMsg.SetCategories(categories))
                } catch (e: Exception) {
                    dispatch(ExercisesMsg.Error(e.message ?: "Ошибка загрузки категорий"))
                    publish(ExercisesLabel.ShowError(e.message ?: "Ошибка загрузки категорий"))
                }
            }
        }

        private fun toggleCategory(id: Int, check: Boolean) {
            val updated = state().selectedCategories.toMutableSet()

            if (check) updated.add(id) else updated.remove(id)

            dispatch(ExercisesMsg.UpdateSelectedCategories(updated))
            loadExercises()
        }
    }

    private object ReducerImpl : Reducer<ExercisesState, ExercisesMsg> {
        override fun ExercisesState.reduce(msg: ExercisesMsg): ExercisesState =
            when (msg) {
                is ExercisesMsg.SetExercises -> copy(exercises = msg.exercises, isLoading = false, error = null)
                is ExercisesMsg.SetQuery -> copy(query = msg.query)
                ExercisesMsg.Loading -> copy(isLoading = true, error = null)
                is ExercisesMsg.Error -> copy(isLoading = false, error = msg.message)
                is ExercisesMsg.SetCategories -> copy(categories = msg.categories)

                is ExercisesMsg.UpdateSelectedCategories ->
                    copy(selectedCategories = msg.selected)
            }
    }
}
