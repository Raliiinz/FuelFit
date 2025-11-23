package com.example.fuelfit.network.client

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.nio.charset.Charset

class LoggingInterceptor : Interceptor {

    companion object {
        private const val TAG = "NetworkLog"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Лог запроса
        val requestBody = request.body
        val buffer = Buffer()
        requestBody?.writeTo(buffer)
        val charset: Charset = requestBody?.contentType()?.charset(Charsets.UTF_8) ?: Charsets.UTF_8

        Log.d(TAG, "➡️ REQUEST: ${request.method} ${request.url}")
        Log.d(TAG, "Headers: ${request.headers}")
        if (requestBody != null) {
            Log.d(TAG, "Body: ${buffer.readString(charset)}")
        }

        val response = chain.proceed(request)

        // Лог ответа
        val responseBody = response.body
        val content = responseBody?.string() ?: ""

        Log.d(TAG, "⬅️ RESPONSE: ${response.code} ${response.message}")
        Log.d(TAG, "Headers: ${response.headers}")
        Log.d(TAG, "Body: $content")

        // Важно: пересоздать тело, чтобы его можно было дальше читать
        val mediaType = responseBody?.contentType() ?: "application/json".toMediaTypeOrNull()
        val newResponseBody = content.toResponseBody(mediaType)

        return response.newBuilder().body(newResponseBody).build()
    }
}
