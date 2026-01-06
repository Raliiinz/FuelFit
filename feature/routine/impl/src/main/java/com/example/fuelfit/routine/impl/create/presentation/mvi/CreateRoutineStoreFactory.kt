package com.example.fuelfit.routine.impl.create.presentation.mvi

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.model.getErrorMessage
import com.example.fuelfit.model.mapApiErrorToUserFriendly
import com.example.fuelfit.routine.api.create.model.RoutineRequest
import com.example.fuelfit.routine.api.create.usecase.CreateRoutineUseCase
import com.example.fuelfit.routine.api.create.usecase.UpdateRoutineUseCase
import com.example.fuelfit.routine.api.list.usecase.GetRoutineUseCase
import kotlinx.coroutines.launch

internal class CreateRoutineStoreFactory(
    private val storeFactory: StoreFactory,
    private val createRoutineUseCase: CreateRoutineUseCase,
    private val updateRoutineUseCase: UpdateRoutineUseCase,
    private val getRoutineByIdUseCase: GetRoutineUseCase
) : CreateRoutineStore.Factory {

    override fun create(routineId: Int?): CreateRoutineStore {
        return object : CreateRoutineStore,
            Store<CreateRoutineIntent, CreateRoutineState, CreateRoutineLabel> by storeFactory.create(
                name = "CreateRoutineStore",
                initialState = CreateRoutineState(),
                executorFactory = { Executor(routineId) },
                bootstrapper = SimpleBootstrapper(Unit),
                reducer = ReducerImpl
            ) {}
    }

    private inner class Executor(private val routineId: Int?) :
        CoroutineExecutor<CreateRoutineIntent, Unit, CreateRoutineState, CreateRoutineMsg, CreateRoutineLabel>() {

        override fun executeIntent(intent: CreateRoutineIntent) {
            when (intent) {
                is CreateRoutineIntent.NameChanged ->
                    dispatch(CreateRoutineMsg.SetName(intent.value))
                is CreateRoutineIntent.DescriptionChanged ->
                    dispatch(CreateRoutineMsg.SetDescription(intent.value))
                is CreateRoutineIntent.StartDateChanged ->
                    dispatch(CreateRoutineMsg.SetStartDate(intent.date))
                is CreateRoutineIntent.EndDateChanged ->
                    dispatch(CreateRoutineMsg.SetEndDate(intent.date))
                is CreateRoutineIntent.FitInWeekChanged ->
                    dispatch(CreateRoutineMsg.SetFitInWeek(intent.value))
                CreateRoutineIntent.Save -> saveRoutine()
                CreateRoutineIntent.BackClicked -> publish(CreateRoutineLabel.Close)
            }
        }

        override fun executeAction(action: Unit) {
            if (routineId != null) loadRoutine(routineId)
        }

        private fun loadRoutine(id: Int) {
            dispatch(CreateRoutineMsg.Loading)
            scope.launch {
                when (val result = getRoutineByIdUseCase(id)) {
                    is ResultWrapper.Success -> {
                        val r = result.data
                        dispatch(
                            CreateRoutineMsg.SetAll(
                                name = r.name,
                                description = r.description ?: "",
                                startDate = r.start,
                                endDate = r.end,
                                fitInWeek = r.fitInWeek
                            )
                        )
                    }
                    is ResultWrapper.Error -> {
                        val message = getErrorMessage(mapApiErrorToUserFriendly(result.error))
                        dispatch(CreateRoutineMsg.Error(message))
                        publish(CreateRoutineLabel.ShowError(message))
                    }
                }
            }
        }

        private fun saveRoutine() {
            if (!state().canSave) return

            dispatch(CreateRoutineMsg.Saving)

            scope.launch {
                val request = RoutineRequest(
                    name = state().name,
                    description = state().description,
                    start = state().startDate,
                    end = state().endDate,
                    fitInWeek = state().fitInWeek,
                    isTemplate = false,
                    isPublic = false
                )

                val result = if (routineId == null) {
                    createRoutineUseCase(request)
                } else {
                    updateRoutineUseCase(routineId, request)
                }

                when (result) {
                    is ResultWrapper.Success -> {
                        publish(CreateRoutineLabel.Close)
                    }
                    is ResultWrapper.Error -> {
                        val userError = mapApiErrorToUserFriendly(result.error)
                        val message = getErrorMessage(userError)
                        dispatch(CreateRoutineMsg.Error(message))
                        publish(CreateRoutineLabel.ShowError(message))
                    }
                }
            }
        }
    }

    private object ReducerImpl : Reducer<CreateRoutineState, CreateRoutineMsg> {
        override fun CreateRoutineState.reduce(msg: CreateRoutineMsg): CreateRoutineState =
            when (msg) {
                is CreateRoutineMsg.SetName -> copy(name = msg.value)
                is CreateRoutineMsg.SetDescription -> copy(description = msg.value)
                is CreateRoutineMsg.SetStartDate -> copy(startDate = msg.date)
                is CreateRoutineMsg.SetEndDate -> copy(endDate = msg.date)
                is CreateRoutineMsg.SetFitInWeek -> copy(fitInWeek = msg.value)
                CreateRoutineMsg.Saving -> copy(isSaving = true, error = null)
                is CreateRoutineMsg.Error -> copy(isSaving = false, error = msg.message)
                is CreateRoutineMsg.Loading -> copy(isLoading = true)
                is CreateRoutineMsg.SetAll -> copy(
                    name = msg.name,
                    description = msg.description,
                    startDate = msg.startDate,
                    endDate = msg.endDate,
                    fitInWeek = msg.fitInWeek,
                    isLoading = false
                )
            }
    }
}
