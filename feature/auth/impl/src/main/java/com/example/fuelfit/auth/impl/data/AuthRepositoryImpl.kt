package com.example.fuelfit.auth.impl.data

import com.example.fuelfit.auth.api.model.AuthToken
import com.example.fuelfit.auth.api.model.LoginParams
import com.example.fuelfit.auth.api.model.RegisterParams
import com.example.fuelfit.auth.api.repository.AuthRepository
import com.example.fuelfit.auth.impl.data.mapper.AuthMapper
import com.example.fuelfit.auth.impl.data.remote.AuthApiService
import com.example.fuelfit.auth.impl.data.remote.dto.UserLoginRequest
import com.example.fuelfit.auth.impl.data.remote.dto.UserRegistrationRequest
import com.example.fuelfit.network.auth.TokenStorage
import com.example.fuelfit.network.safeApiCall
import com.example.fuelfit.model.ResultWrapper

internal class AuthRepositoryImpl(
    private val api: AuthApiService,
    private val tokenStorage: TokenStorage,
    private val mapper: AuthMapper
) : AuthRepository {

    override suspend fun login(request: LoginParams): ResultWrapper<AuthToken> =
        safeApiCall {
            val dto = api.login(
                UserLoginRequest(
                    username = request.username,
                    password = request.password,
                    email = request.email
                )
            )
            val token = mapper.mapLoginToDomain(dto)
            tokenStorage.saveToken(token.token)
            token
        }

    override suspend fun register(request: RegisterParams): ResultWrapper<AuthToken> =
        safeApiCall {
            val dto = api.register(
                UserRegistrationRequest(
                    username = request.username,
                    email = request.email,
                    password = request.password
                )
            )
            val token = mapper.mapLoginToDomain(dto)
            tokenStorage.saveToken(token.token)
            token
        }

    override suspend fun isAuthorized(): ResultWrapper<Boolean> =
        safeApiCall {
            tokenStorage.getToken() != null
        }

    override suspend fun logout(): ResultWrapper<Unit> =
        safeApiCall {
            tokenStorage.clear()
        }
}
