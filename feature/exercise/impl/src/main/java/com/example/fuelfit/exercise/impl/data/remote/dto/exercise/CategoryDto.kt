package com.example.fuelfit.exercise.impl.data.remote.dto.exercise

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val id: Int,
    val name: String
)
