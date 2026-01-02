package com.example.fuelfit.routine.impl.details.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RoutineDayListResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<RoutineDayDto>
)
