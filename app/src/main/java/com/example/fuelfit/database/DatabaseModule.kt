package com.example.fuelfit.database

import androidx.room.Room
import org.koin.dsl.module

internal val databaseModule = module {

    single<AppDatabaseImpl> {
        Room.databaseBuilder(
            get(),
            AppDatabaseImpl::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single<AppDatabase> {
        get<AppDatabaseImpl>()
    }

    single {
        get<AppDatabaseImpl>().userProfileDao()
    }
}
