package com.example.fuelfit.auth.impl.data

import com.example.fuelfit.auth.api.model.AuthTokens
import com.example.fuelfit.auth.api.model.LoginParams
import com.example.fuelfit.auth.api.model.RegisterParams
import com.example.fuelfit.auth.api.repository.AuthRepository
import com.example.fuelfit.auth.impl.data.remote.AuthApiService
import com.example.fuelfit.auth.impl.data.remote.dto.TokenRefreshRequest
import com.example.fuelfit.auth.impl.data.remote.dto.TokenVerifyRequest
import com.example.fuelfit.auth.impl.data.remote.dto.UserRegistrationRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.fuelfit.auth.impl.data.mapper.toDomain
import com.example.fuelfit.auth.impl.data.remote.dto.*
import com.example.fuelfit.network.auth.TokenStorage

class AuthRepositoryImpl(
    private val api: AuthApiService,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    override suspend fun login(request: LoginParams): AuthTokens =
        withContext(Dispatchers.IO) {
            val dto = api.login(
                UserLoginRequest(
                    username = request.username,
                    password = request.password,
                    email = request.email
                )
            )

            val tokens = dto.toDomain()

            tokenStorage.saveAccessToken(tokens.access)

            tokens
        }

    override suspend fun register(request: RegisterParams): AuthTokens =
        withContext(Dispatchers.IO) {
            val dto = api.register(
                UserRegistrationRequest(
                    username = request.username,
                    email = request.email,
                    password = request.password
                )
            )

            val tokens = dto.toDomain()

            tokenStorage.saveAccessToken(tokens.access)

            tokens
        }

    override suspend fun refreshToken(refresh: String): AuthTokens =
        withContext(Dispatchers.IO) {
            val dto = api.refresh(TokenRefreshRequest(refresh))
            val tokens = dto.toDomain(refresh)
            tokenStorage.saveTokens(tokens.access, tokens.refresh ?: "")
            tokens
        }

    override suspend fun verifyToken(token: String): Boolean =
        withContext(Dispatchers.IO) {
            try {
                api.verify(TokenVerifyRequest(token))
                true
            } catch (e: Exception) {
                false
            }
        }

    override suspend fun logout() {
        tokenStorage.clear()
    }
}
