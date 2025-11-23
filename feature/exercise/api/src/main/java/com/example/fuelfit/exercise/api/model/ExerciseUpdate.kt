package com.example.fuelfit.exercise.api.model

data class ExerciseUpdate(
    val category: Int,
    val muscles: List<Int>,
    val musclesSecondary: List<Int>,
    val equipment: List<Int>,
    val variations: Int? = null,
    val licenseAuthor: String? = null
)
