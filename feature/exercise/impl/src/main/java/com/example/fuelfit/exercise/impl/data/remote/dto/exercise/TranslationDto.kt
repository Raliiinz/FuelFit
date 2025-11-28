package com.example.fuelfit.exercise.impl.data.remote.dto.exercise

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslationDto(
    val id: Int,
    val uuid: String,
    val name: String,
    val exercise: Int,
    val description: String,
    val created: String,
    val language: Int,
    val aliases: List<AliasDto> = emptyList(),
    val notes: List<NoteDto> = emptyList(),
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
