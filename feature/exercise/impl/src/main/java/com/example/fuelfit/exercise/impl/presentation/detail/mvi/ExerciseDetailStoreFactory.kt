package com.example.fuelfit.exercise.impl.presentation.detail.mvi

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.exercise.api.usecase.GetExerciseByIdUseCase
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.model.getErrorMessage
import com.example.fuelfit.model.mapApiErrorToUserFriendly
import kotlinx.coroutines.launch

internal class ExerciseDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val getExerciseDetailUseCase: GetExerciseByIdUseCase,
) : ExerciseDetailStore.Factory {

    override fun create(exerciseId: Int): ExerciseDetailStore =
        object : ExerciseDetailStore,
            Store<ExerciseDetailIntent, ExerciseDetailState, ExerciseDetailLabel> by storeFactory.create(
                name = "ExerciseDetailStore",
                initialState = ExerciseDetailState(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = { Executor(exerciseId) },
                reducer = ReducerImpl
            ) {}

    private inner class Executor(
        private val exerciseId: Int
    ) : CoroutineExecutor<ExerciseDetailIntent, Unit, ExerciseDetailState, ExerciseDetailMsg, ExerciseDetailLabel>() {

        override fun executeAction(action: Unit) {
            loadDetail()
        }

        override fun executeIntent(intent: ExerciseDetailIntent) {
            when (intent) {
                ExerciseDetailIntent.Load,
                ExerciseDetailIntent.Retry -> loadDetail()
            }
        }

        private fun loadDetail() {
            dispatch(ExerciseDetailMsg.Loading)
            scope.launch {
                val result = getExerciseDetailUseCase(exerciseId)
                testCrash()
                when (result) {
                    is ResultWrapper.Success -> dispatch(ExerciseDetailMsg.Loaded(result.data))
                    is ResultWrapper.Error -> {
                        val userError = mapApiErrorToUserFriendly(result.error)
                        val message = getErrorMessage(userError)
                        dispatch(ExerciseDetailMsg.Error(message))
                        publish(ExerciseDetailLabel.ShowError(message))
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<ExerciseDetailState, ExerciseDetailMsg> {
        override fun ExerciseDetailState.reduce(msg: ExerciseDetailMsg): ExerciseDetailState =
            when (msg) {
                ExerciseDetailMsg.Loading -> copy(isLoading = true, error = null)
                is ExerciseDetailMsg.Error -> copy(isLoading = false, error = msg.message)
                is ExerciseDetailMsg.Loaded -> copy(isLoading = false, detail = msg.detail, error = null)
            }
    }
}
fun testCrash() {
//    FirebaseCrashlytics.getInstance().log("Test crash from app")
    throw RuntimeException("Test Crash for Firebase")
}