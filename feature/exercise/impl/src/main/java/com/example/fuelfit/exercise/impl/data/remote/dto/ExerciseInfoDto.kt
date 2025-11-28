package com.example.fuelfit.exercise.impl.data.remote.dto

import com.example.fuelfit.exercise.impl.data.remote.dto.exercise.CategoryDto
import com.example.fuelfit.exercise.impl.data.remote.dto.exercise.EquipmentDto
import com.example.fuelfit.exercise.impl.data.remote.dto.exercise.ImageDto
import com.example.fuelfit.exercise.impl.data.remote.dto.exercise.LicenseDto
import com.example.fuelfit.exercise.impl.data.remote.dto.exercise.MuscleDto
import com.example.fuelfit.exercise.impl.data.remote.dto.exercise.TranslationDto
import com.example.fuelfit.exercise.impl.data.remote.dto.exercise.VideoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseInfoDto(
    val id: Int,
    val uuid: String,
    val created: String,
    @SerialName("last_update")
    val lastUpdate: String,
    @SerialName("last_update_global")
    val lastUpdateGlobal: String,
    val category: CategoryDto,
    val muscles: List<MuscleDto>,
    @SerialName("muscles_secondary")
    val musclesSecondary: List<MuscleDto>,
    val equipment: List<EquipmentDto>,
    val license: LicenseDto?,
    @SerialName("license_author")
    val licenseAuthor: String?,
    val images: List<ImageDto>,
    val translations: List<TranslationDto>,
    val variations: Int?,
    val videos: List<VideoDto>,
    @SerialName("author_history")
    val authorHistory: List<String>,
    @SerialName("total_authors_history")
    val totalAuthorsHistory: List<String>
)
