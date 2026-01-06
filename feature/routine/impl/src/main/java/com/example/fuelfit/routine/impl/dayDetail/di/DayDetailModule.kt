package com.example.fuelfit.routine.impl.dayDetail.di

import com.example.fuelfit.routine.api.dayDetail.repository.DayDetailRepository
import com.example.fuelfit.routine.api.dayDetail.usecase.slotEntries.CreateSlotEntryUseCase
import com.example.fuelfit.routine.api.dayDetail.usecase.slotEntries.DeleteSlotEntryUseCase
import com.example.fuelfit.routine.api.dayDetail.usecase.slotEntries.GetSlotEntriesUseCase
import com.example.fuelfit.routine.api.dayDetail.usecase.slotEntries.UpdateSlotEntryUseCase
import com.example.fuelfit.routine.api.dayDetail.usecase.slots.CreateSlotUseCase
import com.example.fuelfit.routine.api.dayDetail.usecase.slots.DeleteSlotUseCase
import com.example.fuelfit.routine.api.dayDetail.usecase.slots.GetSlotsUseCase
import com.example.fuelfit.routine.api.dayDetail.usecase.slots.UpdateSlotUseCase
import com.example.fuelfit.routine.impl.dayDetail.data.DayDetailRepositoryImpl
import com.example.fuelfit.routine.impl.dayDetail.data.mapper.SlotMapper
import com.example.fuelfit.routine.impl.dayDetail.data.remote.SlotApiService
import com.example.fuelfit.routine.impl.dayDetail.data.remote.SlotEntryApiService
import com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slotEntries.CreateSlotEntryUseCaseImpl
import com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slotEntries.DeleteSlotEntryUseCaseImpl
import com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slotEntries.GetSlotEntriesUseCaseImpl
import com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slotEntries.UpdateSlotEntryUseCaseImpl
import com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slots.CreateSlotUseCaseImpl
import com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slots.DeleteSlotUseCaseImpl
import com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slots.GetSlotsUseCaseImpl
import com.example.fuelfit.routine.impl.dayDetail.domain.usecase.slots.UpdateSlotUseCaseImpl
import com.example.fuelfit.routine.impl.dayDetail.presentation.mvi.DayDetailStoreFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal val dayDetailModule = module {

    single { SlotMapper() }

    single<SlotApiService> {
        get<Retrofit>(named("mainRetrofit"))
            .create(SlotApiService::class.java)
    }

    single< SlotEntryApiService> {
        get<Retrofit>(named("mainRetrofit"))
            .create(SlotEntryApiService::class.java)
    }

    single<DayDetailRepository> {
        DayDetailRepositoryImpl(slotApi = get(), slotEntryApi = get(), mapper = get())
    }

    factory<CreateSlotEntryUseCase> { CreateSlotEntryUseCaseImpl(repository = get()) }
    factory<DeleteSlotEntryUseCase> { DeleteSlotEntryUseCaseImpl(repository = get()) }
    factory<GetSlotEntriesUseCase> { GetSlotEntriesUseCaseImpl(repository = get()) }
    factory<UpdateSlotEntryUseCase> { UpdateSlotEntryUseCaseImpl(repository = get()) }
    
    factory<CreateSlotUseCase> { CreateSlotUseCaseImpl(repository = get()) }
    factory<DeleteSlotUseCase> { DeleteSlotUseCaseImpl(repository = get()) }
    factory<GetSlotsUseCase> { GetSlotsUseCaseImpl(repository = get()) }
    factory<UpdateSlotUseCase> { UpdateSlotUseCaseImpl(repository = get()) }


    factory { (DayDetailStoreFactory(
        storeFactory = get(),
        getSlotsUseCase = get(),
        createSlotUseCase = get(),
        updateSlotUseCase = get(),
        deleteSlotUseCase = get(),
        getSlotEntriesUseCase = get(),
        createSlotEntryUseCase = get(),
        updateSlotEntryUseCase = get(),
        deleteSlotEntryUseCase = get(),
        searchExercisesUseCase = get(),
        getExerciseByIdUseCase = get(),
    )) }
}
