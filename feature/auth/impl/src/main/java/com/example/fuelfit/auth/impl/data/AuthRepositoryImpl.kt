package com.example.fuelfit.auth.impl.data

import com.example.fuelfit.auth.api.model.AuthTokens
import com.example.fuelfit.auth.api.model.LoginParams
import com.example.fuelfit.auth.api.model.RegisterParams
import com.example.fuelfit.auth.api.repository.AuthRepository
import com.example.fuelfit.auth.impl.data.mapper.AuthMapper
import com.example.fuelfit.auth.impl.data.remote.AuthApiService
import com.example.fuelfit.auth.impl.data.remote.dto.*
import com.example.fuelfit.model.ResultWrapper
import com.example.fuelfit.network.safeApiCall
import com.example.fuelfit.network.auth.TokenStorage

internal class AuthRepositoryImpl(
    private val api: AuthApiService,
    private val tokenStorage: TokenStorage,
    private val mapper: AuthMapper
) : AuthRepository {

    override suspend fun login(request: LoginParams): ResultWrapper<AuthTokens> =
        safeApiCall {
            val dto = api.login(
                UserLoginRequest(
                    username = request.username,
                    password = request.password,
                    email = request.email
                )
            )
            val tokens = mapper.mapLoginToDomain(dto)
            tokenStorage.saveAccessToken(tokens.access)
            tokens
        }

    override suspend fun register(request: RegisterParams): ResultWrapper<AuthTokens> =
        safeApiCall {
            val dto = api.register(
                UserRegistrationRequest(
                    username = request.username,
                    email = request.email,
                    password = request.password
                )
            )
            val tokens = mapper.mapLoginToDomain(dto)
            tokenStorage.saveAccessToken(tokens.access)
            tokens
        }

    override suspend fun refreshToken(refresh: String): ResultWrapper<AuthTokens> =
        safeApiCall {
            val dto = api.refresh(TokenRefreshRequest(refresh))
            val tokens = mapper.mapTokenRefreshToDomain(dto, refresh)
            tokenStorage.saveTokens(tokens.access, tokens.refresh ?: "")
            tokens
        }

    override suspend fun verifyToken(token: String): ResultWrapper<Boolean> =
        safeApiCall {
            api.verify(TokenVerifyRequest(token))
            true
        }

    override suspend fun isAuthorized(): ResultWrapper<Boolean> =
        safeApiCall {
            tokenStorage.getAccessToken() != null
        }

    override suspend fun logout(): ResultWrapper<Unit> =
        safeApiCall {
            tokenStorage.clear()
        }
}
