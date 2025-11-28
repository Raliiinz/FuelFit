package com.example.fuelfit.exercise.impl.data.remote.dto.exercise

import kotlinx.serialization.Serializable

@Serializable
data class AliasDto(
    val id: Int,
    val uuid: String,
    val alias: String
)
