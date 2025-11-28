package com.example.fuelfit.exercise.impl.data.mapper

import com.example.fuelfit.exercise.api.model.Equipment
import com.example.fuelfit.exercise.api.model.ExerciseAlias
import com.example.fuelfit.exercise.api.model.ExerciseCategory
import com.example.fuelfit.exercise.api.model.ExerciseCreate
import com.example.fuelfit.exercise.api.model.ExerciseImage
import com.example.fuelfit.exercise.api.model.ExerciseInfo
import com.example.fuelfit.exercise.api.model.ExerciseList
import com.example.fuelfit.exercise.api.model.ExerciseNote
import com.example.fuelfit.exercise.api.model.ExerciseTranslation
import com.example.fuelfit.exercise.api.model.ExerciseUpdate
import com.example.fuelfit.exercise.api.model.ExerciseVideo
import com.example.fuelfit.exercise.api.model.License
import com.example.fuelfit.exercise.api.model.Muscle
import com.example.fuelfit.exercise.impl.data.remote.dto.ExerciseUpdateRequest
import com.example.fuelfit.exercise.impl.data.remote.dto.ExerciseCreateRequest
import com.example.fuelfit.exercise.impl.data.remote.dto.ExerciseInfoDto
import com.example.fuelfit.exercise.impl.data.remote.dto.ExerciseInfoListResponseDto
import com.example.fuelfit.exercise.impl.data.remote.dto.exercise.*

//fun ExerciseDto.toDomain() = Exercise(
//    id = id,
//    uuid = uuid,
//    created = created,
//    lastUpdate = lastUpdate,
//    category = category,
//    muscles = muscles,
//    musclesSecondary = musclesSecondary,
//    equipment = equipment,
//    variations = variations,
//    licenseAuthor = licenseAuthor
//)
//
fun ExerciseInfoListResponseDto.toDomain() = ExerciseList(
    count = count,
    next = next,
    previous = previous,
    exercises = results.map { it.toDomain() }
)



fun ExerciseInfoDto.toDomain(): ExerciseInfo =
    ExerciseInfo(
        id = id,
        uuid = uuid,
        created = created,
        lastUpdate = lastUpdate,
        lastUpdateGlobal = lastUpdateGlobal,
        category = category.toDomain(),
        muscles = muscles.map { it.toDomain() },
        musclesSecondary = musclesSecondary.map { it.toDomain() },
        equipment = equipment.map { it.toDomain() },
        license = license?.toDomain(),
        licenseAuthor = licenseAuthor,
        images = images.map { it.toDomain() },
        translations = translations.map { it.toDomain() },
        variations = variations ?: 0,
        videos = videos.map { it.toDomain() },
        authorHistory = authorHistory,
        totalAuthorsHistory = totalAuthorsHistory
    )

fun CategoryDto.toDomain() = ExerciseCategory(id, name)

fun MuscleDto.toDomain() = Muscle(
    id = id,
    name = name,
    nameEn = nameEn,
    isFront = isFront,
    imageUrlMain = imageUrlMain,
    imageUrlSecondary = imageUrlSecondary
)

fun EquipmentDto.toDomain() = Equipment(id, name)

fun LicenseDto.toDomain() = License(id, fullName, shortName, url)

fun ImageDto.toDomain() = ExerciseImage(
    id = id,
    uuid = uuid,
    exercise = exercise,
    exerciseUuid = exerciseUuid,
    image = image,
    isMain = isMain,
    style = style,
    license = license,
    licenseTitle = licenseTitle,
    licenseObjectUrl = licenseObjectUrl,
    licenseAuthor = licenseAuthor,
    licenseAuthorUrl = licenseAuthorUrl,
    licenseDerivativeSourceUrl = licenseDerivativeSourceUrl,
    authorHistory = authorHistory
)

fun TranslationDto.toDomain() = ExerciseTranslation(
    id = id,
    uuid = uuid,
    name = name,
    exercise = exercise,
    description = description,
    created = created,
    language = language,
    aliases = aliases.map { it.toDomain() },
    notes = notes.map { it.toDomain() },
    license = license,
    licenseTitle = licenseTitle,
    licenseObjectUrl = licenseObjectUrl,
    licenseAuthor = licenseAuthor,
    licenseAuthorUrl = licenseAuthorUrl,
    licenseDerivativeSourceUrl = licenseDerivativeSourceUrl,
    authorHistory = authorHistory
)

fun AliasDto.toDomain() = ExerciseAlias(id, uuid, alias)

fun NoteDto.toDomain() = ExerciseNote(id, uuid, translation, comment)

fun VideoDto.toDomain() = ExerciseVideo(
    id = id,
    uuid = uuid,
    exercise = exercise,
    video = video,
    isMain = isMain,
    size = size,
    duration = duration,
    width = width,
    height = height,
    codec = codec,
    codecLong = codecLong,
    license = license,
    licenseTitle = licenseTitle,
    licenseObjectUrl = licenseObjectUrl,
    licenseAuthor = licenseAuthor,
    licenseAuthorUrl = licenseAuthorUrl,
    licenseDerivativeSourceUrl = licenseDerivativeSourceUrl,
    authorHistory = authorHistory
)



fun ExerciseCreate.toDto() = ExerciseCreateRequest(
    category = category,
    muscles = muscles,
    musclesSecondary = musclesSecondary,
    equipment = equipment,
    variations = variations,
    licenseAuthor = licenseAuthor
)

fun ExerciseUpdate.toDto() = ExerciseUpdateRequest(
    category = category,
    muscles = muscles,
    musclesSecondary = musclesSecondary,
    equipment = equipment,
    variations = variations,
    licenseAuthor = licenseAuthor
)