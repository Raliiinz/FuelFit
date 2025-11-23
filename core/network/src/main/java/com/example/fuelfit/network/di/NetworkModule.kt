package com.example.fuelfit.network.di

import com.example.fuelfit.network.BuildConfig.FITNESS_API_URL
import com.example.fuelfit.network.client.LoggingInterceptor
import com.example.fuelfit.network.client.OkHttpProvider
import com.example.fuelfit.network.client.RetrofitProvider
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {

    // -------------------------------
    // Retrofit без авторизации для Auth API
    // -------------------------------
    single<OkHttpClient>(named("authOkHttp")) {
        OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .build() // обычный OkHttp, без TokenInterceptor
    }

    single<Retrofit>(named("authRetrofit")) {
        RetrofitProvider.create(
            baseUrl = FITNESS_API_URL,
            okHttpClient = get(named("authOkHttp"))
        )
    }

    // -------------------------------
    // Retrofit с авторизацией для основного API
    // -------------------------------
    single<OkHttpClient>(named("mainOkHttp")) {
        OkHttpProvider.create(
            tokenStorage = get(),
            tokenRefresher = get()
        )
    }

    single<Retrofit>(named("mainRetrofit")) {
        RetrofitProvider.create(
            baseUrl = FITNESS_API_URL,
            okHttpClient = get(named("mainOkHttp"))
        )
    }
}
