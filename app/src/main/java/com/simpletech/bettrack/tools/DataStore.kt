package com.simpletech.bettrack.tools

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

const val EVENTS_KEY = "EVENTS"
val Context.savedEventsDataStore by preferencesDataStore(EVENTS_KEY)


object DataStoreKeys {
    val SAVED_EVENTS_KEY = stringPreferencesKey("SAVED_EVENTS")
}

suspend fun <T> DataStore<Preferences>.set(
    key: Preferences.Key<T>,
    value: T?,
    deleteIfNull: Boolean = true
) {
    edit { preferences ->
        value?.let {
            preferences[key] = it
        } ?: let {
            if (deleteIfNull) {
                preferences.remove(key)
            }
        }
    }
}

suspend fun <T> DataStore<Preferences>.set(
    key: Preferences.Key<T>, value: T
) {
    set(key, value, false)
}

fun <T> DataStore<Preferences>.get(key: Preferences.Key<T>, default: T): Flow<T> {
    return data.map { preferences ->
        preferences[key] ?: default
    }.distinctUntilChanged()
}

fun <T> DataStore<Preferences>.get(key: Preferences.Key<T>): Flow<T?> {
    return data.map { preferences ->
        preferences[key]
    }.distinctUntilChanged()
}