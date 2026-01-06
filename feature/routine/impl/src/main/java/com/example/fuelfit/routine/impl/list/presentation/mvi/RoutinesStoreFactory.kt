package com.example.fuelfit.routine.impl.list.presentation.mvi

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.model.getErrorMessage
import com.example.fuelfit.model.mapApiErrorToUserFriendly
import com.example.fuelfit.routine.api.list.usecase.DeleteRoutineUseCase
import com.example.fuelfit.routine.api.list.usecase.GetRoutinesUseCase
import kotlinx.coroutines.launch

internal class RoutinesStoreFactory(
    private val storeFactory: StoreFactory,
    private val getRoutinesUseCase: GetRoutinesUseCase,
    private val deleteRoutineUseCase: DeleteRoutineUseCase
) : RoutinesStore.Factory {

    override fun create(): RoutinesStore =
        object : RoutinesStore,
            Store<RoutinesIntent, RoutinesState, RoutinesLabel> by storeFactory.create(
                name = "RoutineStore",
                initialState = RoutinesState(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = { Executor() },
                reducer = ReducerImpl
            ) {}

    private inner class Executor :
        CoroutineExecutor<RoutinesIntent, Unit, RoutinesState, RoutinesMsg, RoutinesLabel>() {

        override fun executeAction(action: Unit) {
            loadRoutines()
        }

        override fun executeIntent(intent: RoutinesIntent) {
            when (intent) {
                RoutinesIntent.Load,
                RoutinesIntent.Retry -> loadRoutines()

                is RoutinesIntent.RoutineClicked ->
                    publish(RoutinesLabel.NavigateToRoutine(intent.id))

                RoutinesIntent.CreateRoutineClicked ->
                    publish(RoutinesLabel.NavigateToCreateRoutine)

                is RoutinesIntent.DeleteRoutine ->
                    deleteRoutine(intent.id)

                is RoutinesIntent.EditRoutine -> publish(RoutinesLabel.NavigateToEditRoutine(intent.id))
            }
        }

        private fun loadRoutines() {
            dispatch(RoutinesMsg.Loading)

            scope.launch {
                when (val result = getRoutinesUseCase()) {
                    is ResultWrapper.Success -> {
                        dispatch(RoutinesMsg.SetRoutines(result.data))
                    }

                    is ResultWrapper.Error -> {
                        val userError = mapApiErrorToUserFriendly(result.error)

                        val message = getErrorMessage(userError)

                        dispatch(RoutinesMsg.Error(message))
                        publish(RoutinesLabel.ShowError(message))
                    }
                }
            }
        }

        private fun deleteRoutine(id: Int) {
            scope.launch {
                when (val result = deleteRoutineUseCase(id)) {
                    is ResultWrapper.Success -> {
                        dispatch(RoutinesMsg.RemoveRoutine(id))
                    }

                    is ResultWrapper.Error -> {
                        val userError = mapApiErrorToUserFriendly(result.error)

                        val message = getErrorMessage(userError)

                        publish(RoutinesLabel.ShowError(message))
                    }
                }
            }
        }
    }

    private object ReducerImpl :
        Reducer<RoutinesState, RoutinesMsg> {

        override fun RoutinesState.reduce(
            msg: RoutinesMsg
        ): RoutinesState =
            when (msg) {
                RoutinesMsg.Loading ->
                    copy(isLoading = true, error = null)

                is RoutinesMsg.SetRoutines ->
                    copy(
                        routines = msg.routines,
                        isLoading = false,
                        error = null
                    )

                is RoutinesMsg.RemoveRoutine ->
                    copy(
                        routines = routines.filterNot {
                            it.id == msg.id
                        }
                    )

                is RoutinesMsg.Error ->
                    copy(
                        isLoading = false,
                        error = msg.message
                    )
            }
    }
}
