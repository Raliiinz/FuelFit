package com.example.fuelfit.exercise.impl.di

import com.example.fuelfit.exercise.api.repository.ExerciseRepository
import com.example.fuelfit.exercise.api.usecase.GetExerciseByIdUseCase
import com.example.fuelfit.exercise.api.usecase.GetExerciseCategoriesUseCase
import com.example.fuelfit.exercise.api.usecase.GetExercisesUseCase
import com.example.fuelfit.exercise.api.usecase.SearchExercisesUseCase
import com.example.fuelfit.exercise.impl.data.ExerciseRepositoryImpl
import com.example.fuelfit.exercise.impl.data.mapper.ExerciseMapper
import com.example.fuelfit.exercise.impl.data.remote.ExerciseApiService
import com.example.fuelfit.exercise.impl.domain.usecase.GetExerciseByIdUseCaseImpl
import com.example.fuelfit.exercise.impl.domain.usecase.GetExerciseCategoriesUseCaseImpl
import com.example.fuelfit.exercise.impl.domain.usecase.GetExercisesUseCaseImpl
import com.example.fuelfit.exercise.impl.domain.usecase.SearchExercisesUseCaseImpl
import com.example.fuelfit.exercise.impl.presentation.detail.mvi.ExerciseDetailStoreFactory
import com.example.fuelfit.exercise.impl.presentation.list.mvi.ExercisesStoreFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val exerciseModule = module {

    single { ExerciseMapper() }

    single<ExerciseApiService> {
        get<Retrofit>(named("mainRetrofit"))
            .create(ExerciseApiService::class.java)
    }

    single<ExerciseRepository> {
        ExerciseRepositoryImpl(api = get(), mapper = get())
    }

    factory<GetExerciseByIdUseCase> { GetExerciseByIdUseCaseImpl(repository = get()) }
    factory<GetExercisesUseCase> { GetExercisesUseCaseImpl(repository = get()) }
    factory<GetExerciseCategoriesUseCase> { GetExerciseCategoriesUseCaseImpl(repository = get()) }
    factory<SearchExercisesUseCase> { SearchExercisesUseCaseImpl(repository = get()) }

    factory { (ExercisesStoreFactory(
        storeFactory = get(),
        getExercisesUseCase = get(),
        getExerciseCategoriesUseCase = get()
    )) }
    factory { (ExerciseDetailStoreFactory(storeFactory = get(), getExerciseDetailUseCase = get())) }
}
