package com.example.fuelfit.routine.impl.details.presentation.mvi

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.model.getErrorMessage
import com.example.fuelfit.model.mapApiErrorToUserFriendly
import com.example.fuelfit.routine.api.details.model.RoutineDayRequest
import com.example.fuelfit.routine.api.details.usecase.CreateRoutineDayUseCase
import com.example.fuelfit.routine.api.details.usecase.DeleteRoutineDayUseCase
import com.example.fuelfit.routine.api.details.usecase.GetRoutineDaysUseCase
import com.example.fuelfit.routine.api.details.usecase.UpdateRoutineDayUseCase
import kotlinx.coroutines.launch

internal class RoutineDayStoreFactory(
    private val storeFactory: StoreFactory,
    private val getRoutineDaysUseCase: GetRoutineDaysUseCase,
    private val createRoutineDayUseCase: CreateRoutineDayUseCase,
    private val updateRoutineDayUseCase: UpdateRoutineDayUseCase,
    private val deleteRoutineDayUseCase: DeleteRoutineDayUseCase
) : RoutineDayStore.Factory {

    override fun create(routineId: Int): RoutineDayStore =
        object : RoutineDayStore,
            Store<RoutineDayIntent, RoutineDayState, RoutineDayLabel> by storeFactory.create(
                name = "RoutineDayStore",
                initialState = RoutineDayState(routineId = routineId),
                bootstrapper = SimpleBootstrapper(RoutineDayAction.Init),
                executorFactory = { Executor() },
                reducer = ReducerImpl
            ) {}

    private inner class Executor :
        CoroutineExecutor<RoutineDayIntent, RoutineDayAction, RoutineDayState, RoutineDayMsg, RoutineDayLabel>() {

        override fun executeAction(action: RoutineDayAction) {
            if (action is RoutineDayAction.Init) {
                loadDays()
            }
        }

        override fun executeIntent(intent: RoutineDayIntent) {
            when (intent) {
                RoutineDayIntent.Init,
                RoutineDayIntent.Refresh -> loadDays()
                is RoutineDayIntent.CreateDay -> createDay(intent.day)
                is RoutineDayIntent.UpdateDay -> updateDay(intent.dayRequest, intent.id)
                is RoutineDayIntent.DeleteDay -> deleteDay(intent.id)
                is RoutineDayIntent.DayClicked -> {
                    if (!intent.day.isRest) {
                        publish(
                            RoutineDayLabel.NavigateToDayDetail(intent.day.id)
                        )
                    }
                }
                RoutineDayIntent.BackClicked -> publish(RoutineDayLabel.NavigateBack)
            }
        }

        private fun loadDays() {
            dispatch(RoutineDayMsg.Loading)

            scope.launch {
                when (
                    val result = getRoutineDaysUseCase(state().routineId)
                ) {
                    is ResultWrapper.Success ->
                        dispatch(
                            RoutineDayMsg.SetDays(result.data)
                        )

                    is ResultWrapper.Error ->
                        handleError(result.error)
                }
            }
        }

        private fun createDay(day: RoutineDayRequest) {
            dispatch(RoutineDayMsg.Loading)
            scope.launch {
                val request = day.copy(routineId = state().routineId)
                when (val result = createRoutineDayUseCase(request)) {
                    is ResultWrapper.Success -> dispatch(RoutineDayMsg.DayCreated(result.data))
                    is ResultWrapper.Error -> handleError(result.error)
                }
            }
        }

        private fun updateDay(dayRequest: RoutineDayRequest, id: Int) {
            dispatch(RoutineDayMsg.Loading)
            scope.launch {
                when (val result = updateRoutineDayUseCase(dayRequest, id)) {
                    is ResultWrapper.Success -> dispatch(RoutineDayMsg.DayUpdated(result.data))
                    is ResultWrapper.Error -> handleError(result.error)
                }
            }
        }

        private fun deleteDay(id: Int) {
            dispatch(RoutineDayMsg.Loading)
            scope.launch {
                when (val result = deleteRoutineDayUseCase(id)) {
                    is ResultWrapper.Success -> dispatch(RoutineDayMsg.DayDeleted(id))
                    is ResultWrapper.Error -> handleError(result.error)
                }
            }
        }

        private fun handleError(
            error: ApiError
        ) {
            val userError = mapApiErrorToUserFriendly(error)
            val message = getErrorMessage(userError)
            dispatch(RoutineDayMsg.Error(message))
            publish(RoutineDayLabel.ShowError(message))
        }
    }

    private object ReducerImpl : Reducer<RoutineDayState, RoutineDayMsg> {
        override fun RoutineDayState.reduce(msg: RoutineDayMsg): RoutineDayState =
            when (msg) {
                RoutineDayMsg.Loading -> copy(isLoading = true, error = null)
                is RoutineDayMsg.SetDays -> copy(days = msg.days, isLoading = false, error = null)
                is RoutineDayMsg.DayCreated -> copy(days = days + msg.day, isLoading = false, error = null)
                is RoutineDayMsg.DayUpdated -> copy(
                    days = days.map { if (it.id == msg.day.id) msg.day else it },
                    isLoading = false,
                    error = null
                )
                is RoutineDayMsg.DayDeleted -> copy(
                    days = days.filterNot { it.id == msg.id },
                    isLoading = false,
                    error = null
                )
                is RoutineDayMsg.Error -> copy(isLoading = false, error = msg.message)
            }
    }
}
