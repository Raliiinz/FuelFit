package com.example.fuelfit.exercise.impl.data.remote.dto.exercise

import kotlinx.serialization.Serializable

@Serializable
data class NoteDto(
    val id: Int,
    val uuid: String,
    val translation: Int,
    val comment: String
)
