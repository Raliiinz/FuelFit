package com.example.fuelfit.routine.impl.list.presentation.mvi

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.routine.api.list.usecase.DeleteRoutineUseCase
import com.example.fuelfit.routine.api.list.usecase.GetRoutinesUseCase
import com.example.fuelfit.routine.impl.list.presentation.mvi.RoutineLabel.*
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
            load()
        }

        override fun executeIntent(intent: RoutineIntent) {
            when (intent) {
                RoutineIntent.Load,
                RoutineIntent.Refresh -> load()

                is RoutineIntent.RoutineClicked ->
                    publish(RoutineLabel.NavigateToRoutine(intent.id))

                is RoutineIntent.DeleteRoutine ->
                    deleteRoutine(intent.id)

                RoutineIntent.CreateRoutineClicked -> {
                    publish(RoutineLabel.NavigateToCreateRoutine)
                }
            }
        }

        private fun load() {
            dispatch(RoutineMsg.Loading)

            scope.launch {
                try {
                    val routines = getRoutinesUseCase()
                    dispatch(RoutineMsg.SetRoutines(routines))
                } catch (e: Exception) {
                    val msg = e.message ?: "Ошибка загрузки тренировок"
                    dispatch(RoutineMsg.Error(msg))
                    publish(ShowError(msg))
                }
            }
        }

        private fun deleteRoutine(id: Int) {
            scope.launch {
                try {
                    deleteRoutineUseCase(id)
                    dispatch(RoutineMsg.RemoveRoutine(id))
                } catch (e: Exception) {
                    publish(ShowError("Не удалось удалить тренировку"))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<RoutineState, RoutineMsg> {
        override fun RoutineState.reduce(msg: RoutineMsg): RoutineState =
            when (msg) {
                RoutineMsg.Loading -> copy(isLoading = true, error = null)

                is RoutineMsg.Error -> copy(
                    isLoading = false,
                    error = msg.message
                )

                is RoutineMsg.SetRoutines -> copy(
                    routines = msg.routines,
                    isLoading = false,
                    error = null
                )

                is RoutineMsg.RemoveRoutine -> copy(
                    routines = routines.filterNot { it.id == msg.id }
                )
            }
    }
}
