package com.example.fuelfit.exercise.impl.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseSearchItemDto(
    val id: Int,
    @SerialName("base_id")
    val baseId: Int,
    val name: String,
    val category: String,
    val image: String? = null,
    @SerialName("image_thumbnail")
    val imageThumbnail: String? = null
)