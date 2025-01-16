package com.kotlinhero.starter.core.data.local.datasources

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

    // When you first retrieve the SharedPreferences instance, they are loaded in totality to an
    // internal Map in memory from disk synchronously on whatever thread you’re using, so it
    // blocks that thread. Then, every time you read a preference value, it’s just pulling it
    // from the in-memory map so there’s no need to worry about what thread you’re on.
    override fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    // When you write preferences using edit, the in-memory Map is immediately
    // updated synchronously. If you use commit() the changes are also written
    // to disk synchronously, thereby blocking the current thread.

    // If you use apply(), the changes are saved to disk asynchronously.
    // Since in most cases SharedPreferences are a very small file, we generally just don’t care
    // about the one-time blocking load of the preferences on the main thread, and we do
    // writes using apply() to avoid blocking more than we have to, since commit() causes main
    // thread hiccups.

    // Changing the coroutineContext to dispatcher.io isn't necessary and using a suspend function
    // is also unnecessary but it's needed in following the interface declaration.
    override suspend fun putString(key: String, value: String?) = withContext(Dispatchers.IO) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override suspend fun remove(key: String) = withContext(Dispatchers.IO) {
        sharedPreferences.edit().remove(key).apply()
    }
}
