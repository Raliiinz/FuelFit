package com.example.fuelfit.exercise.impl.data.remote.dto

import com.example.fuelfit.exercise.impl.data.remote.dto.exercise.CategoryDto
import kotlinx.serialization.Serializable

@Serializable
data class CategoryListResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<CategoryDto>
)
