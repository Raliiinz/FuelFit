package com.example.fuelfit.exercise.impl.presentation.list.mvi

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.exercise.api.model.ExerciseList
import com.example.fuelfit.exercise.api.usecase.GetExerciseCategoriesUseCase
import com.example.fuelfit.exercise.api.usecase.GetExercisesUseCase
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.model.getErrorMessage
import com.example.fuelfit.model.mapApiErrorToUserFriendly
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
                ExercisesIntent.Refresh -> loadExercises(reset = true)
                ExercisesIntent.LoadCategories -> loadCategories()
                is ExercisesIntent.CategoryToggled ->  toggleCategory(intent.categoryId, intent.isChecked)
                ExercisesIntent.LoadNextPage -> {
                    if (state().hasNext && !state().isLoading && !state().isPaging) {
                        loadExercises(reset = false)
                    }
                }
                is ExercisesIntent.ExerciseClicked -> publish(ExercisesLabel.NavigateToExerciseDetail(intent.id))
            }
        }

        private fun loadExercises(reset: Boolean = false) {
            if (state().isLoading || state().isPaging) return

            if (reset) {
                dispatch(ExercisesMsg.ResetPaging)
            }

            dispatch(ExercisesMsg.Loading)

            scope.launch {
                val result = getExercisesUseCase(
                    limit = 20,
                    offset = if (reset) 0 else state().offset,
                    categories = state().selectedCategories.toList()
                )

                when (result) {
                    is ResultWrapper.Success -> {
                        val exerciseList = result.data
                        dispatch(ExercisesMsg.AppendExercises(exerciseList))
                    }
                    is ResultWrapper.Error -> {
                        val userError = mapApiErrorToUserFriendly(result.error)
                        val message = getErrorMessage(userError)
                        dispatch(ExercisesMsg.Error(message))
                        publish(ExercisesLabel.ShowError(message))
                    }
                }
            }
        }

        private fun loadCategories() {
            scope.launch {
                val result = getExerciseCategoriesUseCase()

                when (result) {
                    is ResultWrapper.Success -> dispatch(ExercisesMsg.SetCategories(result.data))
                    is ResultWrapper.Error -> {
                        val userError = mapApiErrorToUserFriendly(result.error)
                        val message = getErrorMessage(userError)
                        dispatch(ExercisesMsg.Error(message))
                        publish(ExercisesLabel.ShowError(message))
                    }
                }
            }
        }

        private fun toggleCategory(id: Int, check: Boolean) {
            val updated = state().selectedCategories.toMutableSet()
            if (check) updated.add(id) else updated.remove(id)
            dispatch(ExercisesMsg.UpdateSelectedCategories(updated))
        }
    }

    private object ReducerImpl : Reducer<ExercisesState, ExercisesMsg> {
        override fun ExercisesState.reduce(msg: ExercisesMsg): ExercisesState =
            when (msg) {
                is ExercisesMsg.SetQuery -> copy(query = msg.query)
                ExercisesMsg.Loading -> copy(
                    isLoading = offset == 0,
                    isPaging = offset > 0
                )
                is ExercisesMsg.Error -> copy(
                    isLoading = false,
                    isPaging = false,
                    error = msg.message
                )
                is ExercisesMsg.SetCategories -> copy(categories = msg.categories)
                is ExercisesMsg.UpdateSelectedCategories -> copy(selectedCategories = msg.selected)
                ExercisesMsg.ResetPaging -> copy(
                    exercises = null,
                    offset = 0,
                    hasNext = true
                )
                is ExercisesMsg.AppendExercises -> {
                    val currentExercises = exercises?.exercises.orEmpty()
                    val newExercises = currentExercises + msg.exerciseList.exercises

                    copy(
                        exercises = ExerciseList(
                            count = msg.exerciseList.count,
                            next = msg.exerciseList.next,
                            previous = msg.exerciseList.previous,
                            exercises = newExercises
                        ),
                        offset = offset + msg.exerciseList.exercises.size,
                        hasNext = msg.exerciseList.next != null,
                        isLoading = false,
                        isPaging = false
                    )
                }
            }
    }
}
