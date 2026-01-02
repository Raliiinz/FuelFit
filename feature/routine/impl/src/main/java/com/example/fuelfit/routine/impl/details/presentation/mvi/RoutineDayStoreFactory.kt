package com.example.fuelfit.routine.impl.details.presentation.mvi

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.routine.api.details.model.RoutineDay
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
                initialState = RoutineDayState(),
                bootstrapper = SimpleBootstrapper(RoutineDayAction.Init),
                executorFactory = { Executor(routineId) },
                reducer = ReducerImpl
            ) {}

    private inner class Executor(private val routineId: Int) :
        CoroutineExecutor<RoutineDayIntent, RoutineDayAction, RoutineDayState, RoutineDayMsg, RoutineDayLabel>() {

        override fun executeAction(action: RoutineDayAction) {
            when (action) {
                RoutineDayAction.Init -> loadDays()
            }
        }

        override fun executeIntent(intent: RoutineDayIntent) {
            when (intent) {
                RoutineDayIntent.Init, RoutineDayIntent.Refresh -> loadDays()
                is RoutineDayIntent.CreateDay -> createDay(intent.day)
                is RoutineDayIntent.UpdateDay -> updateDay(intent.day)
                is RoutineDayIntent.DeleteDay -> deleteDay(intent.id)
                is RoutineDayIntent.DayClicked -> publish(RoutineDayLabel.NavigateToDayDetail(intent.id))
            }
        }

        private fun loadDays() {
            dispatch(RoutineDayMsg.Loading)
            scope.launch {
                try {
                    val result = getRoutineDaysUseCase(routineId)
                    dispatch(RoutineDayMsg.SetDays(result))
                } catch (e: Exception) {
                    dispatch(RoutineDayMsg.Error(e.message ?: "Ошибка загрузки"))
                    publish(RoutineDayLabel.ShowError(e.message ?: "Ошибка загрузки"))
                }
            }
        }

        private fun createDay(day: RoutineDayRequest) {
            dispatch(RoutineDayMsg.Loading)
            scope.launch {
                try {
                    val created = createRoutineDayUseCase(day) // вызываем usecase
                    dispatch(RoutineDayMsg.DayCreated(created))
                } catch (e: Exception) {
                    dispatch(RoutineDayMsg.Error(e.message ?: "Ошибка создания дня"))
                    publish(RoutineDayLabel.ShowError(e.message ?: "Ошибка создания дня"))
                }
            }
        }

        private fun updateDay(day: RoutineDay) {
            dispatch(RoutineDayMsg.Loading)
            scope.launch {
                try {
                    val updated = updateRoutineDayUseCase(day)
                    dispatch(RoutineDayMsg.DayUpdated(updated))
                } catch (e: Exception) {
                    dispatch(RoutineDayMsg.Error(e.message ?: "Ошибка обновления дня"))
                    publish(RoutineDayLabel.ShowError(e.message ?: "Ошибка обновления дня"))
                }
            }
        }

        private fun deleteDay(id: Int) {
            dispatch(RoutineDayMsg.Loading)
            scope.launch {
                try {
                    deleteRoutineDayUseCase(id)
                    dispatch(RoutineDayMsg.DayDeleted(id))
                } catch (e: Exception) {
                    dispatch(RoutineDayMsg.Error(e.message ?: "Ошибка удаления дня"))
                    publish(RoutineDayLabel.ShowError(e.message ?: "Ошибка удаления дня"))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<RoutineDayState, RoutineDayMsg> {
        override fun RoutineDayState.reduce(msg: RoutineDayMsg): RoutineDayState =
            when (msg) {
                RoutineDayMsg.Loading -> copy(isLoading = true, error = null)
                is RoutineDayMsg.SetDays -> copy(days = msg.days, isLoading = false, error = null)
                is RoutineDayMsg.Error -> copy(isLoading = false, error = msg.message)
                is RoutineDayMsg.DayCreated -> copy(days = days + msg.day, isLoading = false, error = null)
                is RoutineDayMsg.DayUpdated -> copy(
                    days = days.map { if (it.id == msg.day.id) msg.day else it },
                    isLoading = false,
                    error = null
                )
                is RoutineDayMsg.DayDeleted -> copy(
                    days = days.filter { it.id != msg.id },
                    isLoading = false,
                    error = null
                )
            }
    }
}
