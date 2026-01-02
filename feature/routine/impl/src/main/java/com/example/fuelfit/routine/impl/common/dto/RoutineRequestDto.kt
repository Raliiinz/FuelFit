package com.example.fuelfit.routine.impl.common.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutineRequestDto(
    val name: String,
    val description: String?,
    val start: String,
    val end: String,
    @SerialName("fit_in_week")
    val fitInWeek: Boolean,
    @SerialName("is_template")
    val isTemplate: Boolean,
    @SerialName("is_public")
    val isPublic: Boolean
)