package com.example.fuelfit.routine.impl.details.di

import com.example.fuelfit.routine.api.details.repository.RoutineDayRepository
import com.example.fuelfit.routine.api.details.usecase.CreateRoutineDayUseCase
import com.example.fuelfit.routine.api.details.usecase.DeleteRoutineDayUseCase
import com.example.fuelfit.routine.api.details.usecase.GetRoutineDayUseCase
import com.example.fuelfit.routine.api.details.usecase.GetRoutineDaysUseCase
import com.example.fuelfit.routine.api.details.usecase.UpdateRoutineDayUseCase
import com.example.fuelfit.routine.impl.details.data.RoutineDayRepositoryImpl
import com.example.fuelfit.routine.impl.details.data.mapper.RoutineDayMapper
import com.example.fuelfit.routine.impl.details.data.remote.RoutineDayApiService
import com.example.fuelfit.routine.impl.details.domain.usecase.CreateRoutineDayUseCaseImpl
import com.example.fuelfit.routine.impl.details.domain.usecase.DeleteRoutineDayUseCaseImpl
import com.example.fuelfit.routine.impl.details.domain.usecase.GetRoutineDayUseCaseImpl
import com.example.fuelfit.routine.impl.details.domain.usecase.GetRoutineDaysUseCaseImpl
import com.example.fuelfit.routine.impl.details.domain.usecase.UpdateRoutineDayUseCaseImpl
import com.example.fuelfit.routine.impl.details.presentation.mvi.RoutineDayStoreFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

internal val routineDayModule = module {

    single { RoutineDayMapper() }

    single<RoutineDayApiService> {
        get<Retrofit>(named("mainRetrofit"))
            .create(RoutineDayApiService::class.java)
    }

    single<RoutineDayRepository> {
        RoutineDayRepositoryImpl(api = get(), mapper = get())
    }

    factory<CreateRoutineDayUseCase> { CreateRoutineDayUseCaseImpl(repository = get()) }
    factory<DeleteRoutineDayUseCase> { DeleteRoutineDayUseCaseImpl(repository = get()) }
    factory<GetRoutineDaysUseCase> { GetRoutineDaysUseCaseImpl(repository = get()) }
    factory<GetRoutineDayUseCase> { GetRoutineDayUseCaseImpl(repository = get()) }
    factory<UpdateRoutineDayUseCase> { UpdateRoutineDayUseCaseImpl(repository = get()) }

    factory { (RoutineDayStoreFactory(
        storeFactory = get(),
        getRoutineDaysUseCase = get(),
        createRoutineDayUseCase = get(),
        updateRoutineDayUseCase = get(),
        deleteRoutineDayUseCase = get(),
    )) }
}
