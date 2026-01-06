package com.example.fuelfit.routine.impl.create.di

import com.example.fuelfit.routine.api.create.repository.CreateRoutineRepository
import com.example.fuelfit.routine.api.create.usecase.CreateRoutineUseCase
import com.example.fuelfit.routine.api.create.usecase.UpdateRoutineUseCase
import com.example.fuelfit.routine.impl.common.RoutineMapper
import com.example.fuelfit.routine.impl.create.data.CreateRoutineRepositoryImpl
import com.example.fuelfit.routine.impl.create.data.remote.CreateRoutineApiService
import com.example.fuelfit.routine.impl.create.domain.usecase.CreateRoutineUseCaseImpl
import com.example.fuelfit.routine.impl.create.domain.usecase.UpdateRoutineUseCaseImpl
import com.example.fuelfit.routine.impl.create.presentation.mvi.CreateRoutineStoreFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal val routineCreateModule = module {

    single { RoutineMapper() }

    single<CreateRoutineApiService> {
        get<Retrofit>(named("mainRetrofit"))
            .create(CreateRoutineApiService::class.java)
    }

    single<CreateRoutineRepository> {
        CreateRoutineRepositoryImpl(api = get(), mapper = get())
    }

    factory<CreateRoutineUseCase> { CreateRoutineUseCaseImpl(repository = get()) }
    factory<UpdateRoutineUseCase> { UpdateRoutineUseCaseImpl(repository = get()) }

    factory { (CreateRoutineStoreFactory(
        storeFactory = get(),
        createRoutineUseCase = get(),
        updateRoutineUseCase = get(),
        getRoutineByIdUseCase = get()
    )) }
}
