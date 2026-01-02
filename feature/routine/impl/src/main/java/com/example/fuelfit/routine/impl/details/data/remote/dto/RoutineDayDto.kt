package com.example.fuelfit.routine.impl.details.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class RoutineDayDto(
    val id: Int,
    val routine: Int,
    val order: Int,
    val name: String,
    val description: String?,
    @SerialName("is_rest")
    val isRest: Boolean,
    @SerialName("need_logs_to_advance")
    val needLogsToAdvance: Boolean,
    val type: RoutineDayTypeDto,
    val config: JsonObject? = null
)
