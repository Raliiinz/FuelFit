package com.example.fuelfit.profile.impl.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    @PrimaryKey val email: String,
    val username: String,
    val isTrustworthy: Boolean,
    val dateJoined: String,
    val gym: Int?,
    val isTemporary: Boolean,
    val lastWorkoutNotification: String?,
    val weightRounding: String?,
    val height: Int?,
    val age: Int?,
    val lastUpdatedAt: Long = System.currentTimeMillis()
)
