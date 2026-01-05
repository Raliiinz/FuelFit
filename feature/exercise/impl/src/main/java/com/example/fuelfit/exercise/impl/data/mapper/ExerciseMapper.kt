package com.example.fuelfit.exercise.impl.data.mapper

import com.example.fuelfit.exercise.api.model.*
import com.example.fuelfit.exercise.impl.data.remote.dto.*
import com.example.fuelfit.exercise.impl.data.remote.dto.exercise.*
import com.example.fuelfit.utils.stripHtml

internal class ExerciseMapper {

    fun mapExerciseList(dto: ExerciseInfoListResponseDto): ExerciseList =
        ExerciseList(
            count = dto.count,
            next = dto.next,
            previous = dto.previous,
            exercises = dto.results.map { mapExerciseInfo(it) }
        )

    fun mapExerciseInfo(dto: ExerciseInfoDto): ExerciseInfo =
        ExerciseInfo(
            id = dto.id,
            uuid = dto.uuid,
            created = dto.created,
            lastUpdate = dto.lastUpdate,
            lastUpdateGlobal = dto.lastUpdateGlobal,
            category = mapCategory(dto.category),
            muscles = dto.muscles.map { mapMuscle(it) },
            musclesSecondary = dto.musclesSecondary.map { mapMuscle(it) },
            equipment = dto.equipment.map { mapEquipment(it) },
            license = dto.license?.let { mapLicense(it) },
            licenseAuthor = dto.licenseAuthor,
            images = dto.images.map { mapImage(it) },
            translations = dto.translations.map { mapTranslation(it) },
            variations = dto.variations ?: 0,
            videos = dto.videos.map { mapVideo(it) },
            authorHistory = dto.authorHistory,
            totalAuthorsHistory = dto.totalAuthorsHistory
        )

    fun mapCategory(dto: CategoryDto) = ExerciseCategory(dto.id, dto.name)

    fun mapMuscle(dto: MuscleDto) = Muscle(
        id = dto.id,
        name = dto.name,
        nameEn = dto.nameEn,
        isFront = dto.isFront,
        imageUrlMain = dto.imageUrlMain,
        imageUrlSecondary = dto.imageUrlSecondary
    )

    fun mapEquipment(dto: EquipmentDto) = Equipment(dto.id, dto.name)

    fun mapLicense(dto: LicenseDto) = License(dto.id, dto.fullName, dto.shortName, dto.url)

    fun mapImage(dto: ImageDto) = ExerciseImage(
        id = dto.id,
        uuid = dto.uuid,
        exercise = dto.exercise,
        exerciseUuid = dto.exerciseUuid,
        image = dto.image,
        isMain = dto.isMain,
        style = dto.style,
        license = dto.license,
        licenseTitle = dto.licenseTitle,
        licenseObjectUrl = dto.licenseObjectUrl,
        licenseAuthor = dto.licenseAuthor,
        licenseAuthorUrl = dto.licenseAuthorUrl,
        licenseDerivativeSourceUrl = dto.licenseDerivativeSourceUrl,
        authorHistory = dto.authorHistory
    )

    fun mapTranslation(dto: TranslationDto) = ExerciseTranslation(
        id = dto.id,
        uuid = dto.uuid,
        name = dto.name,
        exercise = dto.exercise,
        description = dto.description.stripHtml(),
        created = dto.created,
        language = dto.language,
        aliases = dto.aliases.map { mapAlias(it) },
        notes = dto.notes.map { mapNote(it) },
        license = dto.license,
        licenseTitle = dto.licenseTitle,
        licenseObjectUrl = dto.licenseObjectUrl,
        licenseAuthor = dto.licenseAuthor,
        licenseAuthorUrl = dto.licenseAuthorUrl,
        licenseDerivativeSourceUrl = dto.licenseDerivativeSourceUrl,
        authorHistory = dto.authorHistory
    )

    fun mapAlias(dto: AliasDto) = ExerciseAlias(dto.id, dto.uuid, dto.alias)

    fun mapNote(dto: NoteDto) = ExerciseNote(dto.id, dto.uuid, dto.translation, dto.comment)

    fun mapVideo(dto: VideoDto) = ExerciseVideo(
        id = dto.id,
        uuid = dto.uuid,
        exercise = dto.exercise,
        video = dto.video,
        isMain = dto.isMain,
        size = dto.size,
        duration = dto.duration,
        width = dto.width,
        height = dto.height,
        codec = dto.codec,
        codecLong = dto.codecLong,
        license = dto.license,
        licenseTitle = dto.licenseTitle,
        licenseObjectUrl = dto.licenseObjectUrl,
        licenseAuthor = dto.licenseAuthor,
        licenseAuthorUrl = dto.licenseAuthorUrl,
        licenseDerivativeSourceUrl = dto.licenseDerivativeSourceUrl,
        authorHistory = dto.authorHistory
    )

    fun mapSearchItem(dto: ExerciseSearchItemDto) = ExerciseSearchItem(
        id = dto.id,
        baseId = dto.baseId,
        name = dto.name,
        category = dto.category,
        image = dto.image,
        imageThumbnail = dto.imageThumbnail
    )
}
