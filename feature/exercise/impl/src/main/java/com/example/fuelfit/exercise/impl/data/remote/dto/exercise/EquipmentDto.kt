package com.example.fuelfit.exercise.impl.data.remote.dto.exercise

import kotlinx.serialization.Serializable

@Serializable
data class EquipmentDto(
    val id: Int,
    val name: String
)
