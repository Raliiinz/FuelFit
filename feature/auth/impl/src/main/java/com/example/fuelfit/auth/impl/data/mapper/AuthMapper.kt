package com.example.fuelfit.auth.impl.data.mapper

import com.example.fuelfit.auth.api.model.AuthToken
import com.example.fuelfit.auth.impl.data.remote.dto.Login

internal class AuthMapper {

    fun mapLoginToDomain(login: Login): AuthToken {
        return AuthToken(
            token = login.token
        )
    }
}
