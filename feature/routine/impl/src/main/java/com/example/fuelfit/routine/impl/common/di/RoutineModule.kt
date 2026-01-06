package com.example.fuelfit.routine.impl.common.di

import com.example.fuelfit.routine.impl.create.di.routineCreateModule
import com.example.fuelfit.routine.impl.dayDetail.di.dayDetailModule
import com.example.fuelfit.routine.impl.details.di.routineDayModule
import com.example.fuelfit.routine.impl.list.di.routinesListModule
import org.koin.dsl.module

val routineModule = module {
    includes(
        routineCreateModule,
        dayDetailModule,
        routineDayModule,
        routinesListModule
    )
}
