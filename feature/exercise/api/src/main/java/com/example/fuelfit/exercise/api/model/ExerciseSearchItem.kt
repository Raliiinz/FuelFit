package com.example.fuelfit.exercise.api.model

data class ExerciseSearchResponse(
    val value: String,
    val data: ExerciseSearchItem
)

data class ExerciseSearchItem(
    val id: Int,
    val baseId: Int,
    val name: String,
    val category: String,
    val image: String?,
    val imageThumbnail: String?
)
