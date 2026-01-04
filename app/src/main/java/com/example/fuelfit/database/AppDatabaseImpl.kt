package com.example.fuelfit.database

import androidx.room.Database
import com.example.fuelfit.profile.impl.data.local.dao.UserProfileDao
import com.example.fuelfit.profile.impl.data.local.entity.UserProfileEntity

@Database(
    entities = [UserProfileEntity::class],
    version = 1
)
abstract class AppDatabaseImpl : AppDatabase() {
    abstract fun userProfileDao(): UserProfileDao
}
