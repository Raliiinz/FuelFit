package com.example.fuelfit.auth.impl.di

import com.example.fuelfit.auth.api.repository.AuthRepository
import com.example.fuelfit.auth.api.usecase.LoginUseCase
import com.example.fuelfit.auth.api.usecase.LogoutUseCase
import com.example.fuelfit.auth.api.usecase.RegisterUseCase
import com.example.fuelfit.auth.api.usecase.VerifyTokenUseCase
import com.example.fuelfit.auth.impl.data.AuthRepositoryImpl
import com.example.fuelfit.auth.impl.data.remote.AuthApiService
import com.example.fuelfit.auth.impl.data.storage.DataStoreTokenStorage
import com.example.fuelfit.auth.impl.data.storage.TokenRefresherImpl
import com.example.fuelfit.auth.impl.domain.usecase.LoginUseCaseImpl
import com.example.fuelfit.auth.impl.domain.usecase.LogoutUseCaseImpl
import com.example.fuelfit.auth.impl.domain.usecase.RegisterUseCaseImpl
import com.example.fuelfit.auth.impl.domain.usecase.VerifyTokenUseCaseImpl
import com.example.fuelfit.auth.impl.presentation.login.mvi.LoginStoreFactory
import com.example.fuelfit.auth.impl.presentation.register.mvi.RegisterStoreFactory
import com.example.fuelfit.network.auth.TokenRefresher
import com.example.fuelfit.network.auth.TokenStorage
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {

    // DataStore token storage
    single<TokenStorage> {
        DataStoreTokenStorage(context = get())
    }

    // Token refresher (core:network authenticator will use it)
    single<TokenRefresher> {
        TokenRefresherImpl(api = get())
    }

    // -------------------------------
    // Auth API (использует authRetrofit без токенов)
    // -------------------------------
    single<AuthApiService> {
        get<Retrofit>(named("authRetrofit"))
            .create(AuthApiService::class.java)
    }

    // Repo
    single<AuthRepository> {
        AuthRepositoryImpl(api = get(), tokenStorage = get())
    }

    // UseCases
    factory<LoginUseCase> { LoginUseCaseImpl(get()) }
    factory<RegisterUseCase> { RegisterUseCaseImpl(get()) }
    factory<LogoutUseCase> { LogoutUseCaseImpl(get()) }
    factory<VerifyTokenUseCase> { VerifyTokenUseCaseImpl(repository = get()) }


    // Store Factories
    // -----------------------------
    factory { (LoginStoreFactory(storeFactory = get(), loginUseCase = get())) }
    factory { (RegisterStoreFactory(storeFactory = get(), registerUseCase = get())) }
}

