package com.example.fuelfit

import android.app.Application
import com.example.fuelfit.di.initKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
