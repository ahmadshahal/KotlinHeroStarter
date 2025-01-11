package com.kotlinhero.starter.core.foundation.data.local.datasources

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

interface KeyValueStore {

    fun getString(key: String): Flow<String?>

    suspend fun putString(key: String, value: String)

    fun getInt(key: String): Flow<Int?>

    suspend fun putInt(key: String, value: Int)

    fun getBoolean(key: String): Flow<Boolean?>

    suspend fun putBoolean(key: String, value: Boolean)

    fun getFloat(key: String): Flow<Float?>

    suspend fun putFloat(key: String, value: Float)

    fun getLong(key: String): Flow<Long?>

    suspend fun putLong(key: String, value: Long)

    fun getByteArray(key: String): Flow<ByteArray?>

    suspend fun putByteArray(key: String, value: ByteArray)

    suspend fun remove(key: String)

    suspend fun clear()
}

@Factory(binds = [KeyValueStore::class])
internal class KeyValueStoreImpl(
    private val dataStore: DataStore<Preferences>,
) : KeyValueStore {

    override fun getString(key: String): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)]
        }
    }

    override suspend fun putString(key: String, value: String) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    override fun getInt(key: String): Flow<Int?> {
        return dataStore.data.map { preferences ->
            preferences[intPreferencesKey(key)]
        }
    }

    override suspend fun putInt(key: String, value: Int) {
        dataStore.edit { preferences ->
            preferences[intPreferencesKey(key)] = value
        }
    }

    override fun getBoolean(key: String): Flow<Boolean?> {
        return dataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)]
        }
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    override fun getFloat(key: String): Flow<Float?> {
        return dataStore.data.map { preferences ->
            preferences[floatPreferencesKey(key)]
        }
    }

    override suspend fun putFloat(key: String, value: Float) {
        dataStore.edit { preferences ->
            preferences[floatPreferencesKey(key)] = value
        }
    }

    override fun getLong(key: String): Flow<Long?> {
        return dataStore.data.map { preferences ->
            preferences[longPreferencesKey(key)]
        }
    }

    override suspend fun putLong(key: String, value: Long) {
        dataStore.edit { preferences ->
            preferences[longPreferencesKey(key)] = value
        }
    }

    override fun getByteArray(key: String): Flow<ByteArray?> {
        return dataStore.data.map { preferences ->
            preferences[byteArrayPreferencesKey(key)]
        }
    }

    override suspend fun putByteArray(key: String, value: ByteArray) {
        dataStore.edit { preferences ->
            preferences[byteArrayPreferencesKey(key)] = value
        }
    }

    override suspend fun remove(key: String) {
        dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(key))
        }
    }

    override suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
