package com.example.fuelfit.profile.impl.data

import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.network.safeApiCall
import com.example.fuelfit.profile.api.model.UserProfileInfo
import com.example.fuelfit.profile.api.repository.UserProfileRepository
import com.example.fuelfit.profile.impl.data.local.dao.UserProfileDao
import com.example.fuelfit.profile.impl.data.mapper.UserProfileMapper
import com.example.fuelfit.profile.impl.data.remote.UserProfileApiService
import com.example.fuelfit.profile.impl.data.remote.dto.UpdateUserProfileRequest

internal class UserProfileRepositoryImpl(
    private val api: UserProfileApiService,
    private val dao: UserProfileDao,
    private val mapper: UserProfileMapper
) : UserProfileRepository {

    override suspend fun getCurrentUserProfile(): ResultWrapper<UserProfileInfo> {
        val apiResult = safeApiCall {
            api.getCurrentUserProfile()
        }

        when (apiResult) {
            is ResultWrapper.Success -> {
                val entity = mapper.mapDtoToEntity(apiResult.data)
                dao.insertOrUpdate(entity)

                return ResultWrapper.Success(
                    mapper.mapDtoToDomain(apiResult.data)
                )
            }

            is ResultWrapper.Error -> {
                val localProfile = dao.getCurrentProfile()

                return if (localProfile != null) {
                    ResultWrapper.Success(
                        mapper.mapEntityToDomain(localProfile)
                    )
                } else {
                    apiResult
                }
            }
        }
    }

    override suspend fun updateCurrentUserProfile(
        weight: String?,
        height: Int?,
        age: Int?
    ): ResultWrapper<UserProfileInfo> {
        val localProfile = dao.getCurrentProfile()

        val updatedProfile = localProfile?.copy(
            weightRounding = weight ?: localProfile.weightRounding,
            height = height ?: localProfile.height,
            age = age ?: localProfile.age,
            lastUpdatedAt = System.currentTimeMillis()
        )

        if (updatedProfile != null) {
            dao.insertOrUpdate(updatedProfile)
        }

        return safeApiCall {
            val body = UpdateUserProfileRequest(weight, height, age)
            val dto = api.updateCurrentUserProfile(body)
            val entity = mapper.mapDtoToEntity(dto)
            dao.insertOrUpdate(entity)
            mapper.mapDtoToDomain(dto)
        }
    }
}
