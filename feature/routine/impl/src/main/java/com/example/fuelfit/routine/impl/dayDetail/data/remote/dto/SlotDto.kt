package com.example.fuelfit.routine.impl.dayDetail.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SlotDto(
    val id: Int,
    val day: Int,
    val order: Int,
    val comment: String?,
    val config: String?
)
