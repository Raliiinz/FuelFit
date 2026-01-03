package com.example.fuelfit.routine.impl.create.presentation.mvi

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.model.getErrorMessage
import com.example.fuelfit.model.mapApiErrorToUserFriendly
import com.example.fuelfit.routine.api.common.RoutineRequest
import com.example.fuelfit.routine.api.create.usecase.CreateRoutineUseCase
import kotlinx.coroutines.launch

internal class CreateRoutineStoreFactory(
    private val storeFactory: StoreFactory,
    private val createRoutineUseCase: CreateRoutineUseCase
) : CreateRoutineStore.Factory {

    override fun create(): CreateRoutineStore =
        object : CreateRoutineStore,
            Store<CreateRoutineIntent, CreateRoutineState, CreateRoutineLabel> by storeFactory.create(
                name = "CreateRoutineStore",
                initialState = CreateRoutineState(),
                executorFactory = { Executor() },
                reducer = ReducerImpl
            ) {}

    private inner class Executor :
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

                val result = createRoutineUseCase(request)

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
            }
    }
}
