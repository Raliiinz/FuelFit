package com.example.fuelfit.exercise.impl.data.mapper

import com.example.fuelfit.exercise.api.model.Exercise
import com.example.fuelfit.exercise.api.model.ExerciseCreate
import com.example.fuelfit.exercise.api.model.ExerciseList
import com.example.fuelfit.exercise.api.model.ExerciseUpdate
import com.example.fuelfit.exercise.impl.data.remote.dto.ExerciseUpdateRequest
import com.example.fuelfit.exercise.impl.data.remote.dto.ExerciseCreateRequest
import com.example.fuelfit.exercise.impl.data.remote.dto.ExerciseDto
import com.example.fuelfit.exercise.impl.data.remote.dto.ExerciseListResponseDto

fun ExerciseDto.toDomain() = Exercise(
    id = id,
    uuid = uuid,
    created = created,
    lastUpdate = lastUpdate,
    category = category,
    muscles = muscles,
    musclesSecondary = musclesSecondary,
    equipment = equipment,
    variations = variations,
    licenseAuthor = licenseAuthor
)

fun ExerciseListResponseDto.toDomain() = ExerciseList(
    count = count,
    next = next,
    previous = previous,
    results = results.map { it.toDomain() }
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