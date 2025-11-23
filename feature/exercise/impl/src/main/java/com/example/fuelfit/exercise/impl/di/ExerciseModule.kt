package com.example.fuelfit.exercise.impl.di

import com.example.fuelfit.exercise.api.repository.ExerciseRepository
import com.example.fuelfit.exercise.api.usecase.CreateExerciseUseCase
import com.example.fuelfit.exercise.api.usecase.DeleteExerciseUseCase
import com.example.fuelfit.exercise.api.usecase.GetExerciseByIdUseCase
import com.example.fuelfit.exercise.api.usecase.GetExercisesUseCase
import com.example.fuelfit.exercise.api.usecase.UpdateExerciseUseCase
import com.example.fuelfit.exercise.impl.data.ExerciseRepositoryImpl
import com.example.fuelfit.exercise.impl.data.remote.ExerciseApiService
import com.example.fuelfit.exercise.impl.domain.usecase.CreateExerciseUseCaseImpl
import com.example.fuelfit.exercise.impl.domain.usecase.DeleteExerciseUseCaseImpl
import com.example.fuelfit.exercise.impl.domain.usecase.GetExerciseByIdUseCaseImpl
import com.example.fuelfit.exercise.impl.domain.usecase.GetExercisesUseCaseImpl
import com.example.fuelfit.exercise.impl.domain.usecase.UpdateExerciseUseCaseImpl
import com.example.fuelfit.exercise.impl.presentation.list.mvi.ExerciseStoreFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val exerciseModule = module {

    // Exercise API
    single<ExerciseApiService> {
        get<Retrofit>(named("mainRetrofit"))
            .create(ExerciseApiService::class.java)
    }

    // Repository
    single<ExerciseRepository> {
        ExerciseRepositoryImpl(api = get())
    }

    // -------------------------------
    // UseCases
    // UseCases
    factory<CreateExerciseUseCase> { CreateExerciseUseCaseImpl(get()) }
    factory<UpdateExerciseUseCase> { UpdateExerciseUseCaseImpl(get()) }
    factory<DeleteExerciseUseCase> { DeleteExerciseUseCaseImpl(get()) }
    factory<GetExerciseByIdUseCase> { GetExerciseByIdUseCaseImpl(repository = get()) }
    factory<GetExercisesUseCase> { GetExercisesUseCaseImpl(repository = get()) }


    // Store Factories
    // -----------------------------
    factory { (ExerciseStoreFactory(storeFactory = get(), getExercisesUseCase = get())) }
}
