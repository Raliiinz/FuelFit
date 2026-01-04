package com.example.fuelfit.profile.impl.di

import com.example.fuelfit.profile.api.repository.UserProfileRepository
import com.example.fuelfit.profile.api.usecase.GetCurrentUserProfileUseCase
import com.example.fuelfit.profile.api.usecase.UpdateCurrentUserProfileUseCase
import com.example.fuelfit.profile.impl.data.UserProfileRepositoryImpl
import com.example.fuelfit.profile.impl.data.mapper.UserProfileMapper
import com.example.fuelfit.profile.impl.data.remote.UserProfileApiService
import com.example.fuelfit.profile.impl.domain.usecase.GetCurrentUserProfileUseCaseImpl
import com.example.fuelfit.profile.impl.domain.usecase.UpdateCurrentUserProfileUseCaseImpl
import com.example.fuelfit.profile.impl.presentation.mvi.UserProfileStoreFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val userProfileModule = module {

    single { UserProfileMapper() }

    single<UserProfileApiService> {
        get<Retrofit>(named("mainRetrofit"))
            .create(UserProfileApiService::class.java)
    }

    single<UserProfileRepository> {
        UserProfileRepositoryImpl(api = get(), dao = get(), mapper = get())
    }

    factory<GetCurrentUserProfileUseCase> { GetCurrentUserProfileUseCaseImpl(repository = get()) }
    factory<UpdateCurrentUserProfileUseCase> { UpdateCurrentUserProfileUseCaseImpl(repository = get()) }

    factory {
        UserProfileStoreFactory(
            storeFactory = get(),
            getCurrentUserProfileUseCase = get(),
            updateCurrentUserProfileUseCase = get(),
            logoutUseCase = get(),
        )
    }
}
