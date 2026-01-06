package com.example.fuelfit.exercise.impl.testutils

import com.example.fuelfit.exercise.api.model.ExerciseCategory
import com.example.fuelfit.exercise.api.model.ExerciseInfo
import com.example.fuelfit.exercise.api.model.ExerciseList
import com.example.fuelfit.exercise.api.model.ExerciseSearchItem

object ExerciseTestData {

    fun makeExerciseInfo(
        id: Int = 1,
        nameUuid: String = "uuid-$id",
        categoryId: Int = 1,
        categoryName: String = "Strength"
    ) = ExerciseInfo(
        id = id,
        uuid = nameUuid,
        created = "2026-01-01T00:00:00Z",
        lastUpdate = "2026-01-01T00:00:00Z",
        lastUpdateGlobal = "2026-01-01T00:00:00Z",
        category = ExerciseCategory(categoryId, categoryName),
        muscles = emptyList(),
        musclesSecondary = emptyList(),
        equipment = emptyList(),
        license = null,
        licenseAuthor = null,
        images = emptyList(),
        translations = emptyList(),
        variations = 0,
        videos = emptyList(),
        authorHistory = emptyList(),
        totalAuthorsHistory = emptyList()
    )

    fun makeExerciseList(vararg exercises: ExerciseInfo) = ExerciseList(
        count = exercises.size,
        next = null,
        previous = null,
        exercises = exercises.toList()
    )

    fun makeExerciseSearchItem(
        id: Int = 1,
        baseId: Int = 100,
        name: String = "Exercise",
        category: String = "Strength"
    ) = ExerciseSearchItem(
        id = id,
        baseId = baseId,
        name = name,
        category = category,
        image = null,
        imageThumbnail = null
    )
}
