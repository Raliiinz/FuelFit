package com.example.fuelfit.routine.impl.dayDetail.presentation.mvi

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.fuelfit.exercise.api.usecase.GetExerciseByIdUseCase
import com.example.fuelfit.exercise.api.usecase.SearchExercisesUseCase
import com.example.fuelfit.model.ApiError
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.model.getErrorMessage
import com.example.fuelfit.model.mapApiErrorToUserFriendly
import com.example.fuelfit.routine.api.dayDetail.model.Slot
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntry
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntryRequest
import com.example.fuelfit.routine.api.dayDetail.model.SlotRequest
import com.example.fuelfit.routine.api.dayDetail.usecase.slotEntries.CreateSlotEntryUseCase
import com.example.fuelfit.routine.api.dayDetail.usecase.slotEntries.DeleteSlotEntryUseCase
import com.example.fuelfit.routine.api.dayDetail.usecase.slotEntries.GetSlotEntriesUseCase
import com.example.fuelfit.routine.api.dayDetail.usecase.slotEntries.UpdateSlotEntryUseCase
import com.example.fuelfit.routine.api.dayDetail.usecase.slots.CreateSlotUseCase
import com.example.fuelfit.routine.api.dayDetail.usecase.slots.DeleteSlotUseCase
import com.example.fuelfit.routine.api.dayDetail.usecase.slots.GetSlotsUseCase
import com.example.fuelfit.routine.api.dayDetail.usecase.slots.UpdateSlotUseCase
import kotlinx.coroutines.launch

internal class DayDetailStoreFactory(
    private val storeFactory: StoreFactory,
    private val getSlotsUseCase: GetSlotsUseCase,
    private val createSlotUseCase: CreateSlotUseCase,
    private val updateSlotUseCase: UpdateSlotUseCase,
    private val deleteSlotUseCase: DeleteSlotUseCase,
    private val getSlotEntriesUseCase: GetSlotEntriesUseCase,
    private val createSlotEntryUseCase: CreateSlotEntryUseCase,
    private val updateSlotEntryUseCase: UpdateSlotEntryUseCase,
    private val deleteSlotEntryUseCase: DeleteSlotEntryUseCase,
    private val searchExercisesUseCase: SearchExercisesUseCase,
    private val getExerciseByIdUseCase: GetExerciseByIdUseCase
) : DayDetailStore.Factory {

    override fun create(dayId: Int): DayDetailStore =
        object : DayDetailStore,
            Store<DayDetailIntent, DayDetailState, DayDetailLabel> by storeFactory.create(
                name = "DayDetailStore",
                initialState = DayDetailState(dayId = dayId),
                bootstrapper = SimpleBootstrapper(DayDetailAction.Init),
                executorFactory = { Executor(dayId) },
                reducer = ReducerImpl
            ) {}

    @Suppress("TooManyFunctions")
    private inner class Executor(
        private val dayId: Int
    ) : CoroutineExecutor<DayDetailIntent, DayDetailAction, DayDetailState, DayDetailMsg, DayDetailLabel>() {

        override fun executeAction(action: DayDetailAction) {
            if (action is DayDetailAction.Init) {
                load()
            }
        }

        override fun executeIntent(intent: DayDetailIntent) {
            when (intent) {
                DayDetailIntent.Init,
                DayDetailIntent.Refresh -> load()

                is DayDetailIntent.CreateSlot -> createSlot(intent.request)
                is DayDetailIntent.UpdateSlot -> updateSlot(intent.id, intent.request)
                is DayDetailIntent.DeleteSlot -> deleteSlot(intent.id)

                is DayDetailIntent.CreateEntry -> createEntry(intent.request)
                is DayDetailIntent.UpdateEntry -> updateEntry(intent.id, intent.request)
                is DayDetailIntent.DeleteEntry -> deleteEntry(intent.id)

                is DayDetailIntent.SlotClicked -> publish(
                    DayDetailLabel.ShowSlotDetails(intent.id)
                )
                is DayDetailIntent.SearchExercises -> searchExercises(intent.query)
                is DayDetailIntent.SelectExercise -> selectExercise(
                    intent.slotId, intent.exerciseId, intent.exerciseName
                )
                is DayDetailIntent.OpenSearch -> dispatch(DayDetailMsg.SearchOpened(intent.slotId))
                DayDetailIntent.CloseSearch -> dispatch(DayDetailMsg.SearchClosed)

                DayDetailIntent.BackClicked -> publish(DayDetailLabel.NavigateBack)
            }
        }

        private fun load() {
            dispatch(DayDetailMsg.Loading)

            scope.launch {
                when (val result = getSlotsUseCase(dayId)) {
                    is ResultWrapper.Success -> loadEntries(result.data)
                    is ResultWrapper.Error -> handleError(result.error)
                }
            }
        }

        private suspend fun loadEntries(slots: List<Slot>) {
            val entriesBySlot = mutableMapOf<Int, List<SlotEntry>>()
            val namesById = mutableMapOf<Int, String>()

            slots.forEach { slot ->
                val slotEntriesResult = getSlotEntriesUseCase(slot.id)
                if (slotEntriesResult is ResultWrapper.Error) {
                    handleError(slotEntriesResult.error)
                    return
                }

                val slotEntries = (slotEntriesResult as ResultWrapper.Success).data
                entriesBySlot[slot.id] = slotEntries

                slotEntries.forEach { entry ->
                    if (entry.exerciseId !in namesById) {
                        namesById[entry.exerciseId] = getExerciseName(entry.exerciseId)
                    }
                }
            }

            dispatch(DayDetailMsg.SetSlots(slots, entriesBySlot))
            namesById.forEach { (id, name) ->
                dispatch(DayDetailMsg.ExerciseNameMapped(id, name))
            }
        }

        private suspend fun getExerciseName(exerciseId: Int): String {
            return when (val exerciseResult = getExerciseByIdUseCase(exerciseId)) {
                is ResultWrapper.Success -> exerciseResult.data.translations.firstOrNull()?.name
                    ?: "Exercise #$exerciseId"
                is ResultWrapper.Error -> "Exercise #$exerciseId"
            }
        }

        private fun createSlot(request: SlotRequest) {
            dispatch(DayDetailMsg.Loading)
            scope.launch {
                when (val result = createSlotUseCase(request)) {
                    is ResultWrapper.Success -> dispatch(DayDetailMsg.SlotCreated(result.data))
                    is ResultWrapper.Error -> handleError(result.error)
                }
            }
        }

        private fun updateSlot(id: Int, request: SlotRequest) {
            dispatch(DayDetailMsg.Loading)
            scope.launch {
                when (val result = updateSlotUseCase(id, request)) {
                    is ResultWrapper.Success -> dispatch(DayDetailMsg.SlotUpdated(result.data))
                    is ResultWrapper.Error -> handleError(result.error)
                }
            }
        }

        private fun deleteSlot(id: Int) {
            dispatch(DayDetailMsg.Loading)
            scope.launch {
                when (val result = deleteSlotUseCase(id)) {
                    is ResultWrapper.Success -> dispatch(DayDetailMsg.SlotDeleted(id))
                    is ResultWrapper.Error -> handleError(result.error)
                }
            }
        }

        private fun createEntry(request: SlotEntryRequest) {
            dispatch(DayDetailMsg.Loading)

            scope.launch {
                when (val result = createSlotEntryUseCase(request)) {
                    is ResultWrapper.Success -> {
                        dispatch(DayDetailMsg.EntryCreated(result.data))

                        val exerciseId = result.data.exerciseId
                        if (exerciseId !in state().exerciseNamesById) {
                            val name = getExerciseName(exerciseId)
                            dispatch(DayDetailMsg.ExerciseNameMapped(exerciseId, name))
                        }
                    }
                    is ResultWrapper.Error -> handleError(result.error)
                }
            }
        }

        private fun updateEntry(id: Int, request: SlotEntryRequest) {
            dispatch(DayDetailMsg.Loading)
            scope.launch {
                when (val result = updateSlotEntryUseCase(id, request)) {
                    is ResultWrapper.Success -> dispatch(DayDetailMsg.EntryUpdated(result.data))
                    is ResultWrapper.Error -> handleError(result.error)
                }
            }
        }

        private fun deleteEntry(id: Int) {
            dispatch(DayDetailMsg.Loading)
            scope.launch {
                when (val result = deleteSlotEntryUseCase(id)) {
                    is ResultWrapper.Success -> dispatch(DayDetailMsg.EntryDeleted(id))
                    is ResultWrapper.Error -> handleError(result.error)
                }
            }
        }

        private fun searchExercises(query: String) {
            if (query.isBlank()) {
                dispatch(DayDetailMsg.SearchSuccess(emptyList()))
                return
            }

            dispatch(DayDetailMsg.SearchStarted(query))
            scope.launch {
                when (val result = searchExercisesUseCase(term = query, language = "en,ru")) {
                    is ResultWrapper.Success -> dispatch(DayDetailMsg.SearchSuccess(result.data))
                    is ResultWrapper.Error -> handleError(result.error)
                }
            }
        }

        private fun selectExercise(slotId: Int, exerciseId: Int, exerciseName: String) {
            createEntry(SlotEntryRequest(slotId = slotId, exerciseId = exerciseId))
            dispatch(DayDetailMsg.ExerciseNameMapped(exerciseId = exerciseId, name = exerciseName))
            dispatch(DayDetailMsg.SearchClosed)
        }

        private fun handleError(error: ApiError) {
            val message = getErrorMessage(mapApiErrorToUserFriendly(error))
            dispatch(DayDetailMsg.Error(message))
            publish(DayDetailLabel.ShowError(message))
        }
    }

    private object ReducerImpl : Reducer<DayDetailState, DayDetailMsg> {
        override fun DayDetailState.reduce(msg: DayDetailMsg): DayDetailState = when (msg) {
            DayDetailMsg.Loading -> copy(isLoading = true, error = null)
            is DayDetailMsg.SetSlots -> copy(
                slots = msg.slots, entriesBySlot = msg.entries, isLoading = false
            )
            is DayDetailMsg.SlotCreated -> copy(
                slots = slots + msg.slot, isLoading = false
            )
            is DayDetailMsg.SlotUpdated -> copy(
                slots = slots.map { if (it.id == msg.slot.id) msg.slot else it },
                isLoading = false
            )
            is DayDetailMsg.SlotDeleted -> copy(slots = slots.filterNot { it.id == msg.id }, isLoading = false)
            is DayDetailMsg.EntryCreated -> copy(
                entriesBySlot = entriesBySlot + (
                        msg.entry.slotId to (entriesBySlot[msg.entry.slotId].orEmpty() + msg.entry)
                ),
                isLoading = false
            )
            is DayDetailMsg.EntryUpdated -> copy(
                entriesBySlot = entriesBySlot.mapValues {
                    (_, list) -> list.map {
                        if (it.id == msg.entry.id) msg.entry else it
                    }
                },
                isLoading = false
            )
            is DayDetailMsg.EntryDeleted -> copy(
                entriesBySlot = entriesBySlot.mapValues { (_, list) -> list.filterNot { it.id == msg.id } },
                isLoading = false
            )
            is DayDetailMsg.SearchStarted -> copy(
                searchQuery = msg.query, isSearching = true, searchResults = emptyList()
            )
            is DayDetailMsg.SearchSuccess -> copy(
                searchResults = msg.result, isSearching = false
            )
            is DayDetailMsg.SearchOpened -> copy(
                searchSlotId = msg.slotId, searchQuery = "", searchResults = emptyList()
            )
            DayDetailMsg.SearchClosed -> copy(
                searchSlotId = null, searchQuery = "", searchResults = emptyList(), isSearching = false
            )
            is DayDetailMsg.Error -> copy(
                isLoading = false, error = msg.message
            )
            is DayDetailMsg.ExerciseNameMapped -> copy(
                exerciseNamesById = exerciseNamesById + (msg.exerciseId to msg.name)
            )
        }
    }
}
