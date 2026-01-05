package com.example.fuelfit.auth.impl.data.remote

import com.example.fuelfit.auth.impl.data.remote.dto.Login
import com.example.fuelfit.auth.impl.data.remote.dto.UserLoginRequest
import com.example.fuelfit.auth.impl.data.remote.dto.UserRegistrationRequest
import retrofit2.http.Body
import retrofit2.http.POST


internal interface AuthApiService {

    @POST("api/v2/login/")
    suspend fun login(@Body body: UserLoginRequest): Login

    @POST("api/v2/register/")
    suspend fun register(@Body body: UserRegistrationRequest): Login
}
