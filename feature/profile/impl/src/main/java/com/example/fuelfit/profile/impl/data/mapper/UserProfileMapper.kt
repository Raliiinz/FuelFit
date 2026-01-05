package com.example.fuelfit.profile.impl.data.mapper

import com.example.fuelfit.profile.api.model.UserProfileInfo
import com.example.fuelfit.profile.impl.data.local.entity.UserProfileEntity
import com.example.fuelfit.profile.impl.data.remote.dto.UserProfileInfoDto
import com.example.fuelfit.utils.datetime.toOffsetDateTime
import java.time.LocalDate

internal class UserProfileMapper {

    fun mapDtoToDomain(dto: UserProfileInfoDto): UserProfileInfo = UserProfileInfo(
        username = dto.username,
        email = dto.email,
        isTrustworthy = dto.isTrustworthy,
        dateJoined = dto.dateJoined.toOffsetDateTime(),
        gym = dto.gym,
        isTemporary = dto.isTemporary,
        lastWorkoutNotification = dto.lastWorkoutNotification?.let { LocalDate.parse(it) },
        weightRounding = dto.weightRounding,
        height = dto.height,
        age = dto.age
    )

    fun mapDtoToEntity(dto: UserProfileInfoDto): UserProfileEntity = UserProfileEntity(
        email = dto.email,
        username = dto.username,
        isTrustworthy = dto.isTrustworthy,
        dateJoined = dto.dateJoined,
        gym = dto.gym,
        isTemporary = dto.isTemporary,
        lastWorkoutNotification = dto.lastWorkoutNotification,
        weightRounding = dto.weightRounding,
        height = dto.height,
        age = dto.age,
        lastUpdatedAt = System.currentTimeMillis()
    )

    fun mapEntityToDomain(entity: UserProfileEntity): UserProfileInfo = UserProfileInfo(
        username = entity.username,
        email = entity.email,
        isTrustworthy = entity.isTrustworthy,
        dateJoined = entity.dateJoined.toOffsetDateTime(),
        gym = entity.gym,
        isTemporary = entity.isTemporary,
        lastWorkoutNotification = entity.lastWorkoutNotification?.let { LocalDate.parse(it) },
        weightRounding = entity.weightRounding,
        height = entity.height,
        age = entity.age
    )
}
