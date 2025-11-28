package com.example.fuelfit.exercise.api.model

data class ExerciseInfo(
    val id: Int,
    val uuid: String,
    val created: String,
    val lastUpdate: String,
    val lastUpdateGlobal: String,
    val category: ExerciseCategory,
    val muscles: List<Muscle>,
    val musclesSecondary: List<Muscle>,
    val equipment: List<Equipment>,
    val license: License?,
    val licenseAuthor: String?,
    val images: List<ExerciseImage>,
    val translations: List<ExerciseTranslation>,
    val variations: Int,
    val videos: List<ExerciseVideo>,
    val authorHistory: List<String>,
    val totalAuthorsHistory: List<String>
) {
    fun musclesLine(): String {
        val primary = muscles.map { it.nameEn?.ifEmpty { it.name } }
        val secondary = musclesSecondary.map { it.nameEn?.ifEmpty { it.name } }

        return buildString {
            append(primary.joinToString(", "))
            if (secondary.isNotEmpty()) {
                append(" (secondary: ")
                append(secondary.joinToString(", "))
                append(")")
            }
        }
    }
}

data class ExerciseCategory(
    val id: Int,
    val name: String
)

data class Muscle(
    val id: Int,
    val name: String,
    val nameEn: String?,
    val isFront: Boolean,
    val imageUrlMain: String,
    val imageUrlSecondary: String
)

data class Equipment(
    val id: Int,
    val name: String
)

data class License(
    val id: Int,
    val fullName: String,
    val shortName: String,
    val url: String?
)

data class ExerciseImage(
    val id: Int,
    val uuid: String,
    val exercise: Int,
    val exerciseUuid: String,
    val image: String,
    val isMain: Boolean,
    val style: Int?,
    val license: Int?,
    val licenseTitle: String?,
    val licenseObjectUrl: String?,
    val licenseAuthor: String?,
    val licenseAuthorUrl: String?,
    val licenseDerivativeSourceUrl: String?,
    val authorHistory: List<String>
)

data class ExerciseTranslation(
    val id: Int,
    val uuid: String,
    val name: String,
    val exercise: Int,
    val description: String,
    val created: String,
    val language: Int,
    val aliases: List<ExerciseAlias>,
    val notes: List<ExerciseNote>,
    val license: Int?,
    val licenseTitle: String?,
    val licenseObjectUrl: String?,
    val licenseAuthor: String?,
    val licenseAuthorUrl: String?,
    val licenseDerivativeSourceUrl: String?,
    val authorHistory: List<String>
)

data class ExerciseAlias(
    val id: Int,
    val uuid: String,
    val alias: String
)

data class ExerciseNote(
    val id: Int,
    val uuid: String,
    val translation: Int,
    val comment: String
)

data class ExerciseVideo(
    val id: Int,
    val uuid: String,
    val exercise: Int,
    val video: String,
    val isMain: Boolean,
    val size: Int,
    val duration: String,
    val width: Int,
    val height: Int,
    val codec: String,
    val codecLong: String,
    val license: Int?,
    val licenseTitle: String?,
    val licenseObjectUrl: String?,
    val licenseAuthor: String?,
    val licenseAuthorUrl: String?,
    val licenseDerivativeSourceUrl: String?,
    val authorHistory: List<String>
)
