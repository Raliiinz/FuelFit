package com.example.fuelfit.di

import android.app.Application
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.fuelfit.auth.impl.di.authModule
import com.example.fuelfit.exercise.impl.di.exerciseModule
import com.example.fuelfit.network.di.networkModule
import com.example.fuelfit.routine.impl.common.di.routineModule
import com.example.fuelfit.ui.splash.di.splashModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

val appModule = module {
    single<StoreFactory> { DefaultStoreFactory() }

    includes(
        networkModule,
        splashModule,
        authModule,
        exerciseModule,
        routineModule,
        // другие фичи
    )
}

fun Application.initKoin() {
    startKoin {
        androidContext(this@initKoin)
        modules(appModule)
    }
}
