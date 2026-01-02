package com.example.fuelfit.routine.api.dayDetail.model

data class Slot(
    val id: Int,
    val dayId: Int,
    val order: Int,
    val comment: String?,
    val config: String?
)
