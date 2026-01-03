package com.example.fuelfit.routine.api.dayDetail.model

data class SlotRequest(
    val dayId: Int,
    val order: Int,
    val comment: String = "",
    val config: String? = null
)
