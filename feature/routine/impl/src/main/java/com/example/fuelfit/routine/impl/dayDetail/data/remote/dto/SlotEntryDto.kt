package com.example.fuelfit.routine.impl.dayDetail.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SlotEntryDto(
    val id: Int,
    val slot: Int,
    val exercise: Int,
    val type: SlotEntryTypeDto,
    @SerialName("repetition_unit")
    val repetitionUnit: Int?,
    @SerialName("repetition_rounding")
    val repetitionRounding: String?,
    @SerialName("weight_unit")
    val weightUnit: Int?,
    @SerialName("weight_rounding")
    val weightRounding: String?,
    val order: Int,
    val comment: String?,
    val config: String?,
)
