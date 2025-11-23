package com.example.fuelfit.auth.impl.data.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fuelfit.network.auth.TokenStorage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("auth_tokens")

class DataStoreTokenStorage(
    private val context: Context
) : TokenStorage {

    private val ACCESS = stringPreferencesKey("access_token")
    private val REFRESH = stringPreferencesKey("refresh_token")

    override suspend fun getAccessToken(): String? =
        context.dataStore.data.map { it[ACCESS] }.first()

    override suspend fun getRefreshToken(): String? =
        context.dataStore.data.map { it[REFRESH] }.first()

    override suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { it[ACCESS] = token }
    }

    override suspend fun saveRefreshToken(token: String) {
        context.dataStore.edit { it[REFRESH] = token }
    }

    override suspend fun clear() {
        context.dataStore.edit {
            it.remove(ACCESS)
            it.remove(REFRESH)
        }
    }

    override suspend fun saveTokens(access: String, refresh: String) {
        context.dataStore.edit {
            it[ACCESS] = access
            it[REFRESH] = refresh
        }
    }
}

