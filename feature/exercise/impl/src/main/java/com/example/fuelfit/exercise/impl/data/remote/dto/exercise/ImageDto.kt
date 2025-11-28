package com.example.fuelfit.exercise.impl.data.remote.dto.exercise

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageDto(
    val id: Int,
    val uuid: String,
    val exercise: Int,
    @SerialName("exercise_uuid")
    val exerciseUuid: String,
    val image: String,
    @SerialName("is_main")
    val isMain: Boolean,
    val style: Int?,
    val license: Int?,
    @SerialName("license_title")
    val licenseTitle: String?,
    @SerialName("license_object_url")
    val licenseObjectUrl: String?,
    @SerialName("license_author")
    val licenseAuthor: String?,
    @SerialName("license_author_url")
    val licenseAuthorUrl: String?,
    @SerialName("license_derivative_source_url")
    val licenseDerivativeSourceUrl: String?,
    @SerialName("author_history")
    val authorHistory: List<String> = emptyList()
)
