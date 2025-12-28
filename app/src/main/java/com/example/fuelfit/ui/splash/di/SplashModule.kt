package com.example.fuelfit.ui.splash.di

import com.example.fuelfit.ui.splash.mvi.SplashStoreFactory
import org.koin.dsl.module

val splashModule = module {
    factory {
        SplashStoreFactory(
            storeFactory = get(),
            isAuthorizedUseCase = get()
        )
    }
}
