package com.example.fuelfit.exercise.api.model

data class ExerciseList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val exercises: List<ExerciseInfo>
)