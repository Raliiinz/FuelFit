package com.example.fuelfit.auth.impl.data.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fuelfit.network.auth.TokenStorage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = DataStoreTokenStorage.DATASTORE_NAME)

internal class DataStoreTokenStorage(
    private val context: Context
) : TokenStorage {

    override suspend fun getToken(): String? =
        context.dataStore.data.map { it[TOKEN_KEY] }.first()

    override suspend fun saveToken(token: String) {
        context.dataStore.edit { it[TOKEN_KEY] = token }
    }

    override suspend fun clear() {
        context.dataStore.edit { it.remove(TOKEN_KEY) }
    }

    companion object {
        const val DATASTORE_NAME = "auth_token"
        val TOKEN_KEY = stringPreferencesKey("token")
    }
}
