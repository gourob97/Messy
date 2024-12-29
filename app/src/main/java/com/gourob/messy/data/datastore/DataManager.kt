package com.gourob.messy.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_preferences")

object DataStoreManager {
    private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    private val IS_REGISTERED = booleanPreferencesKey("is_registered")

    fun isLoggedIn(context: Context): Flow<Boolean> =
        context.dataStore.data.map { preferences -> preferences[IS_LOGGED_IN] ?: false }

    fun isRegistered(context: Context): Flow<Boolean> =
        context.dataStore.data.map { preferences -> preferences[IS_REGISTERED] ?: false }


    suspend fun setLoggedIn(context: Context, value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = value
        }
    }

    suspend fun setRegistered(context: Context, value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_REGISTERED] = value
        }
    }
}