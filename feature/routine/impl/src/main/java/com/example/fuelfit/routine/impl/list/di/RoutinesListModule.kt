package com.example.fuelfit.routine.impl.list.di

import com.example.fuelfit.routine.api.list.repository.RoutinesRepository
import com.example.fuelfit.routine.api.list.usecase.DeleteRoutineUseCase
import com.example.fuelfit.routine.api.list.usecase.GetRoutineUseCase
import com.example.fuelfit.routine.api.list.usecase.GetRoutinesUseCase
import com.example.fuelfit.routine.impl.common.RoutineMapper
import com.example.fuelfit.routine.impl.list.data.RoutinesRepositoryImpl
import com.example.fuelfit.routine.impl.list.data.remote.RoutinesApiService
import com.example.fuelfit.routine.impl.list.domain.usecase.DeleteRoutineUseCaseImpl
import com.example.fuelfit.routine.impl.list.domain.usecase.GetRoutineUseCaseImpl
import com.example.fuelfit.routine.impl.list.domain.usecase.GetRoutinesUseCaseImpl
import com.example.fuelfit.routine.impl.list.presentation.mvi.RoutinesStoreFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal val routinesListModule = module {

    single { RoutineMapper() }

    single<RoutinesApiService> {
        get<Retrofit>(named("mainRetrofit"))
            .create(RoutinesApiService::class.java)
    }

    single<RoutinesRepository> {
        RoutinesRepositoryImpl(api = get(), mapper = get())
    }

    factory<DeleteRoutineUseCase> { DeleteRoutineUseCaseImpl(repository = get()) }
    factory<GetRoutinesUseCase> { GetRoutinesUseCaseImpl(repository = get()) }
    factory<GetRoutineUseCase> { GetRoutineUseCaseImpl(repository = get()) }

    factory { (RoutinesStoreFactory(
        storeFactory = get(),
        getRoutinesUseCase = get(),
        deleteRoutineUseCase = get(),
    )) }
}
