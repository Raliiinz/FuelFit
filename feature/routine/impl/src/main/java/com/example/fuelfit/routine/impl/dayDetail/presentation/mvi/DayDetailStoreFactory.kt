package com.example.fuelfit.routine.impl.dayDetail.presentation.mvi

import com.arkivanov.mvikotlin.core.store.*
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
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
import com.example.fuelfit.routine.impl.dayDetail.presentation.mvi.DayDetailMsg.*
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

    private inner class Executor(
        private val dayId: Int
    ) : CoroutineExecutor<
            DayDetailIntent,
            DayDetailAction,
            DayDetailState,
            DayDetailMsg,
            DayDetailLabel>() {

        override fun executeAction(action: DayDetailAction) {
            if (action is DayDetailAction.Init) {
                load()
            }
        }

        override fun executeIntent(intent: DayDetailIntent) {
            when (intent) {
                DayDetailIntent.Init,
                DayDetailIntent.Refresh -> load()

                is DayDetailIntent.CreateSlot ->
                    createSlot(intent.request)

                is DayDetailIntent.UpdateSlot ->
                    updateSlot(intent.id, intent.request)

                is DayDetailIntent.DeleteSlot ->
                    deleteSlot(intent.id)

                is DayDetailIntent.CreateEntry ->
                    createEntry(intent.request)

                is DayDetailIntent.UpdateEntry ->
                    updateEntry(intent.id, intent.request)

                is DayDetailIntent.DeleteEntry ->
                    deleteEntry(intent.id)

                is DayDetailIntent.SlotClicked ->
                    publish(
                        DayDetailLabel.ShowSlotDetails(
                            intent.id
                        )
                    )

                is DayDetailIntent.SearchExercises ->
                    searchExercises(intent.query)

                is DayDetailIntent.SelectExercise ->
                    selectExercise(
                        intent.slotId,
                        intent.exerciseId
                    )

                is DayDetailIntent.OpenSearch ->
                    dispatch(
                        SearchOpened(
                            slotId = intent.slotId
                        )
                    )

                DayDetailIntent.CloseSearch ->
                    dispatch(SearchClosed)
            }
        }

        private fun load() {
            dispatch(Loading)

            scope.launch {
                when (val slotsResult = getSlotsUseCase(dayId)) {
                    is ResultWrapper.Success ->
                        loadEntries(slotsResult.data)

                    is ResultWrapper.Error ->
                        handleError(
                            slotsResult.error,
                            "Ошибка загрузки слотов"
                        )
                }
            }
        }

        private suspend fun loadEntries(slots: List<Slot>) {
            val entriesBySlot = mutableMapOf<Int, List<SlotEntry>>()

            for (slot in slots) {
                when (val result = getSlotEntriesUseCase(slot.id)) {
                    is ResultWrapper.Success ->
                        entriesBySlot[slot.id] = result.data

                    is ResultWrapper.Error -> {
                        handleError(
                            result.error,
                            "Ошибка загрузки упражнений"
                        )
                        return
                    }
                }
            }

            dispatch(
                SetSlots(
                    slots = slots,
                    entries = entriesBySlot
                )
            )
        }

        private fun createSlot(request: SlotRequest) {
            dispatch(Loading)

            scope.launch {
                when (val result = createSlotUseCase(request)) {
                    is ResultWrapper.Success ->
                        dispatch(
                            SlotCreated(result.data)
                        )

                    is ResultWrapper.Error ->
                        handleError(
                            result.error,
                            "Ошибка создания слота"
                        )
                }
            }
        }

        private fun updateSlot(id: Int, request: SlotRequest) {
            dispatch(Loading)

            scope.launch {
                when (val result = updateSlotUseCase(id, request)) {
                    is ResultWrapper.Success ->
                        dispatch(
                            SlotUpdated(result.data)
                        )

                    is ResultWrapper.Error ->
                        handleError(
                            result.error,
                            "Ошибка обновления слота"
                        )
                }
            }
        }

        private fun deleteSlot(id: Int) {
            dispatch(Loading)

            scope.launch {
                when (val result = deleteSlotUseCase(id)) {
                    is ResultWrapper.Success ->
                        dispatch(
                            SlotDeleted(id)
                        )

                    is ResultWrapper.Error ->
                        handleError(
                            result.error,
                            "Ошибка удаления слота"
                        )
                }
            }
        }

        private fun createEntry(request: SlotEntryRequest) {
            dispatch(Loading)

            scope.launch {
                when (val result = createSlotEntryUseCase(request)) {
                    is ResultWrapper.Success ->
                        dispatch(
                            EntryCreated(result.data)
                        )

                    is ResultWrapper.Error ->
                        handleError(
                            result.error,
                            "Ошибка создания упражнения"
                        )
                }
            }
        }

        private fun updateEntry(id: Int, request: SlotEntryRequest) {
            dispatch(Loading)

            scope.launch {
                when (val result = updateSlotEntryUseCase(id, request)) {
                    is ResultWrapper.Success ->
                        dispatch(
                            EntryUpdated(result.data)
                        )

                    is ResultWrapper.Error ->
                        handleError(
                            result.error,
                            "Ошибка обновления упражнения"
                        )
                }
            }
        }

        private fun deleteEntry(id: Int) {
            dispatch(Loading)

            scope.launch {
                when (val result = deleteSlotEntryUseCase(id)) {
                    is ResultWrapper.Success ->
                        dispatch(
                            EntryDeleted(id)
                        )

                    is ResultWrapper.Error ->
                        handleError(
                            result.error,
                            "Ошибка удаления упражнения"
                        )
                }
            }
        }

        private fun searchExercises(query: String) {
            if (query.isBlank()) {
                dispatch(SearchSuccess(emptyList()))
                return
            }

            dispatch(SearchStarted(query))

            scope.launch {
                when (
                    val result = searchExercisesUseCase(
                        term = query,
                        language = "en,ru"
                    )
                ) {
                    is ResultWrapper.Success ->
                        dispatch(
                            SearchSuccess(result.data)
                        )

                    is ResultWrapper.Error ->
                        handleError(
                            result.error,
                            "Ошибка поиска упражнений"
                        )
                }
            }
        }

        private fun selectExercise(
            slotId: Int,
            exerciseId: Int
        ) {
            createEntry(
                SlotEntryRequest(
                    slotId = slotId,
                    exerciseId = exerciseId
                )
            )

            dispatch(SearchClosed)
        }

        private fun handleError(
            error: ApiError,
            fallback: String
        ) {
            val userError =
                mapApiErrorToUserFriendly(error)

            val message =
                getErrorMessage(userError)

            dispatch(Error(message))
            publish(DayDetailLabel.ShowError(message))
        }
    }

    private object ReducerImpl :
        Reducer<DayDetailState, DayDetailMsg> {

        override fun DayDetailState.reduce(
            msg: DayDetailMsg
        ): DayDetailState =
            when (msg) {
                Loading ->
                    copy(
                        isLoading = true,
                        error = null
                    )

                is SetSlots ->
                    copy(
                        slots = msg.slots,
                        entriesBySlot = msg.entries,
                        isLoading = false
                    )

                is SlotCreated ->
                    copy(
                        slots = slots + msg.slot,
                        isLoading = false
                    )

                is SlotUpdated ->
                    copy(
                        slots = slots.map {
                            if (it.id == msg.slot.id)
                                msg.slot
                            else it
                        },
                        isLoading = false
                    )

                is SlotDeleted ->
                    copy(
                        slots = slots.filterNot {
                            it.id == msg.id
                        },
                        isLoading = false
                    )

                is EntryCreated ->
                    copy(
                        entriesBySlot =
                            entriesBySlot + (
                                    msg.entry.slotId to
                                            (
                                                    entriesBySlot[msg.entry.slotId]
                                                        .orEmpty() + msg.entry
                                                    )
                                    ),
                        isLoading = false
                    )

                is EntryUpdated ->
                    copy(
                        entriesBySlot =
                            entriesBySlot.mapValues { (_, list) ->
                                list.map {
                                    if (it.id == msg.entry.id)
                                        msg.entry
                                    else it
                                }
                            },
                        isLoading = false
                    )

                is EntryDeleted ->
                    copy(
                        entriesBySlot =
                            entriesBySlot.mapValues { (_, list) ->
                                list.filterNot {
                                    it.id == msg.id
                                }
                            },
                        isLoading = false
                    )

                is SearchStarted ->
                    copy(
                        searchQuery = msg.query,
                        isSearching = true,
                        searchResults = emptyList()
                    )

                is SearchSuccess ->
                    copy(
                        searchResults = msg.result,
                        isSearching = false
                    )

                is SearchOpened ->
                    copy(
                        searchSlotId = msg.slotId,
                        searchQuery = "",
                        searchResults = emptyList()
                    )

                SearchClosed ->
                    copy(
                        searchSlotId = null,
                        searchQuery = "",
                        searchResults = emptyList(),
                        isSearching = false
                    )

                is Error ->
                    copy(
                        isLoading = false,
                        error = msg.message
                    )
            }
    }
}
