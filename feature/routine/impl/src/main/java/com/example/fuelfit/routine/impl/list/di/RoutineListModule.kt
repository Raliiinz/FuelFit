package com.example.fuelfit.routine.impl.list.di

import com.example.fuelfit.routine.api.list.repository.RoutineRepository
import com.example.fuelfit.routine.api.list.usecase.DeleteRoutineUseCase
import com.example.fuelfit.routine.api.list.usecase.GetRoutineUseCase
import com.example.fuelfit.routine.api.list.usecase.GetRoutinesUseCase
import com.example.fuelfit.routine.impl.common.RoutineMapper
import com.example.fuelfit.routine.impl.list.data.RoutineRepositoryImpl
import com.example.fuelfit.routine.impl.list.data.remote.RoutineApiService
import com.example.fuelfit.routine.impl.list.domain.usecase.DeleteRoutineUseCaseImpl
import com.example.fuelfit.routine.impl.list.domain.usecase.GetRoutineUseCaseImpl
import com.example.fuelfit.routine.impl.list.domain.usecase.GetRoutinesUseCaseImpl
import com.example.fuelfit.routine.impl.list.presentation.mvi.RoutineStoreFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal val routineListModule = module {

    single { RoutineMapper() }

    single<RoutineApiService> {
        get<Retrofit>(named("mainRetrofit"))
            .create(RoutineApiService::class.java)
    }

    single<RoutineRepository> {
        RoutineRepositoryImpl(api = get(), mapper = get())
    }

    factory<DeleteRoutineUseCase> { DeleteRoutineUseCaseImpl(repository = get()) }
    factory<GetRoutinesUseCase> { GetRoutinesUseCaseImpl(repository = get()) }
    factory<GetRoutineUseCase> { GetRoutineUseCaseImpl(repository = get()) }

    factory { (RoutineStoreFactory(
        storeFactory = get(),
        getRoutinesUseCase = get(),
        deleteRoutineUseCase = get(),
    )) }
}
