package com.example.fuelfit.routine.impl.list.presentation.mvi

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.model.getErrorMessage
import com.example.fuelfit.model.mapApiErrorToUserFriendly
import com.example.fuelfit.routine.api.list.usecase.DeleteRoutineUseCase
import com.example.fuelfit.routine.api.list.usecase.GetRoutinesUseCase
import kotlinx.coroutines.launch

internal class RoutineStoreFactory(
    private val storeFactory: StoreFactory,
    private val getRoutinesUseCase: GetRoutinesUseCase,
    private val deleteRoutineUseCase: DeleteRoutineUseCase
) : RoutineStore.Factory {

    override fun create(): RoutineStore =
        object : RoutineStore,
            Store<RoutineIntent, RoutineState, RoutineLabel> by storeFactory.create(
                name = "RoutineStore",
                initialState = RoutineState(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = { Executor() },
                reducer = ReducerImpl
            ) {}

    private inner class Executor :
        CoroutineExecutor<RoutineIntent, Unit, RoutineState, RoutineMsg, RoutineLabel>() {

        override fun executeAction(action: Unit) {
            loadRoutines()
        }

        override fun executeIntent(intent: RoutineIntent) {
            when (intent) {
                RoutineIntent.Load,
                RoutineIntent.Refresh -> loadRoutines()

                is RoutineIntent.RoutineClicked ->
                    publish(RoutineLabel.NavigateToRoutine(intent.id))

                RoutineIntent.CreateRoutineClicked ->
                    publish(RoutineLabel.NavigateToCreateRoutine)

                is RoutineIntent.DeleteRoutine ->
                    deleteRoutine(intent.id)
            }
        }

        private fun loadRoutines() {
            dispatch(RoutineMsg.Loading)

            scope.launch {
                when (val result = getRoutinesUseCase()) {
                    is ResultWrapper.Success -> {
                        dispatch(RoutineMsg.SetRoutines(result.data))
                    }

                    is ResultWrapper.Error -> {
                        val userError =
                            mapApiErrorToUserFriendly(result.error)

                        val message =
                            getErrorMessage(userError)

                        dispatch(RoutineMsg.Error(message))
                        publish(RoutineLabel.ShowError(message))
                    }
                }
            }
        }

        private fun deleteRoutine(id: Int) {
            scope.launch {
                when (val result = deleteRoutineUseCase(id)) {
                    is ResultWrapper.Success -> {
                        dispatch(RoutineMsg.RemoveRoutine(id))
                    }

                    is ResultWrapper.Error -> {
                        val userError =
                            mapApiErrorToUserFriendly(result.error)

                        val message =
                            getErrorMessage(userError)

                        publish(RoutineLabel.ShowError(message))
                    }
                }
            }
        }
    }

    private object ReducerImpl :
        Reducer<RoutineState, RoutineMsg> {

        override fun RoutineState.reduce(
            msg: RoutineMsg
        ): RoutineState =
            when (msg) {
                RoutineMsg.Loading ->
                    copy(isLoading = true, error = null)

                is RoutineMsg.SetRoutines ->
                    copy(
                        routines = msg.routines,
                        isLoading = false,
                        error = null
                    )

                is RoutineMsg.RemoveRoutine ->
                    copy(
                        routines = routines.filterNot {
                            it.id == msg.id
                        }
                    )

                is RoutineMsg.Error ->
                    copy(
                        isLoading = false,
                        error = msg.message
                    )
            }
    }
}
