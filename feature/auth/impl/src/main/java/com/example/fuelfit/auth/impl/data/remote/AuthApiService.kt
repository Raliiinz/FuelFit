package com.example.fuelfit.auth.impl.data.remote

import com.example.fuelfit.auth.impl.data.remote.dto.Login
import com.example.fuelfit.auth.impl.data.remote.dto.TokenObtainPair
import com.example.fuelfit.auth.impl.data.remote.dto.TokenObtainPairRequest
import com.example.fuelfit.auth.impl.data.remote.dto.TokenRefresh
import com.example.fuelfit.auth.impl.data.remote.dto.TokenRefreshRequest
import com.example.fuelfit.auth.impl.data.remote.dto.TokenVerifyRequest
import com.example.fuelfit.auth.impl.data.remote.dto.UserLoginRequest
import com.example.fuelfit.auth.impl.data.remote.dto.UserRegistrationRequest
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApiService {

    @POST("api/v2/login/")
    suspend fun login(@Body body: UserLoginRequest): Login

    @POST("api/v2/register/")
    suspend fun register(@Body body: UserRegistrationRequest): Login

    @POST("api/v2/token/")
    suspend fun token(@Body body: TokenObtainPairRequest): TokenObtainPair

    @POST("api/v2/token/refresh")
    suspend fun refresh(@Body body: TokenRefreshRequest): TokenRefresh

    @POST("api/v2/token/verify")
    suspend fun verify(@Body body: TokenVerifyRequest)
}
