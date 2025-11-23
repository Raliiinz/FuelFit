package com.example.fuelfit.network.serializer

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object KotlinSerializationProvider {

    val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    fun converterFactory(): Converter.Factory =
        json.asConverterFactory("application/json".toMediaType())
}
