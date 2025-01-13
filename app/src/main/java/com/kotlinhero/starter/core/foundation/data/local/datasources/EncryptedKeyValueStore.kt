package com.kotlinhero.starter.core.foundation.data.local.datasources

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory

interface EncryptedKeyValueStore {

    fun getString(key: String): String?

    suspend fun putString(key: String, value: String?)

    suspend fun remove(key: String)
}

@Factory(binds = [EncryptedKeyValueStore::class])
internal class EncryptedKeyValueStoreImpl(
    private val sharedPreferences: SharedPreferences
) : EncryptedKeyValueStore {

    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override suspend fun putString(key: String, value: String?) = withContext(Dispatchers.IO) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override suspend fun remove(key: String) = withContext(Dispatchers.IO) {
        sharedPreferences.edit().remove(key).apply()
    }
}
