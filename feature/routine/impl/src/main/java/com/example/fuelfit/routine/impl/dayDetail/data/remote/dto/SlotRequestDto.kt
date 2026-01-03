package com.example.fuelfit.routine.impl.dayDetail.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SlotRequestDto(
    val day: Int,
    val order: Int,
    val comment: String,
    val config: String?
)
