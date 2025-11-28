package com.example.fuelfit.exercise.impl.data.remote.dto.exercise

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MuscleDto(
    val id: Int,
    val name: String,
    @SerialName("name_en")
    val nameEn: String?,
    @SerialName("is_front")
    val isFront: Boolean,
    @SerialName("image_url_main")
    val imageUrlMain: String,
    @SerialName("image_url_secondary")
    val imageUrlSecondary: String
)
