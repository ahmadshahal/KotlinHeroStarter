package com.kotlinhero.starter.features.auth.data.local.datasources

import androidx.datastore.core.DataStore
import com.kotlinhero.starter.features.auth.domain.entities.CipherText
import com.kotlinhero.starter.core.data.local.datasources.EncryptedKeyValueStore
import com.kotlinhero.starter.core.data.local.datasources.KeyValueStore
import com.kotlinhero.starter.features.auth.data.local.models.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Named

private const val ACCESS_TOKEN_KEY = "accessToken"
private const val REFRESH_TOKEN_KEY = "refreshToken"
private const val ONBOARDING_VISITED_TOKEN_KEY = "onboardingVisited"
private const val ENCRYPTED_TOKEN_DATA_STORE_KEY = "encrypted_token_data_store_key"

interface AuthLocalStorage {
    val userFlow: Flow<UserPreferences>

    fun getAccessToken(): String?

    suspend fun getUser(): UserPreferences?

    fun getRefreshToken(): String?

    suspend fun setAccessToken(token: String)

    suspend fun setRefreshToken(token: String)

    suspend fun setUser(userPreferences: UserPreferences)

    suspend fun isOnboardingVisited(): Boolean

    suspend fun setOnboardingVisited()

    suspend fun getSecretToken(): CipherText?

    suspend fun setSecretToken(cipherText: CipherText)

    suspend fun removeSecretToken()

    suspend fun removeAccessToken()

    suspend fun removeRefreshToken()

    suspend fun removeUser()
}

@Factory(binds = [AuthLocalStorage::class])
internal class AuthLocalStorageImpl(
    private val encryptedKeyValueStore: EncryptedKeyValueStore,
    private val keyValueStore: KeyValueStore,
    @Named("UserDataStore") private val userDataStore: DataStore<UserPreferences>,
) : AuthLocalStorage {

    override val userFlow: Flow<UserPreferences>
        get() = userDataStore.data

    override suspend fun getSecretToken(): CipherText? {
        val json =
            keyValueStore.getString(ENCRYPTED_TOKEN_DATA_STORE_KEY).firstOrNull() ?: return null
        return Json.decodeFromString(CipherText.serializer(), json)
    }

    override fun getAccessToken(): String? = encryptedKeyValueStore.getString(ACCESS_TOKEN_KEY)

    override suspend fun getUser(): UserPreferences? =
        userDataStore.data.firstOrNull()

    override fun getRefreshToken(): String? = encryptedKeyValueStore.getString(REFRESH_TOKEN_KEY)

    override suspend fun setAccessToken(token: String) =
        encryptedKeyValueStore.putString(ACCESS_TOKEN_KEY, token)

    override suspend fun isOnboardingVisited(): Boolean =
        keyValueStore.getBoolean(ONBOARDING_VISITED_TOKEN_KEY).first() ?: false

    override suspend fun setRefreshToken(token: String) =
        encryptedKeyValueStore.putString(REFRESH_TOKEN_KEY, token)

    override suspend fun setUser(userPreferences: UserPreferences) {
        userDataStore.updateData { userPreferences }
    }

    override suspend fun setSecretToken(cipherText: CipherText) {
        val json = Json.encodeToString(CipherText.serializer(), cipherText)
        keyValueStore.putString(ENCRYPTED_TOKEN_DATA_STORE_KEY, json)
    }

    override suspend fun setOnboardingVisited() =
        keyValueStore.putBoolean(ONBOARDING_VISITED_TOKEN_KEY, true)

    override suspend fun removeAccessToken() =
        encryptedKeyValueStore.remove(ACCESS_TOKEN_KEY)

    override suspend fun removeRefreshToken() =
        encryptedKeyValueStore.remove(REFRESH_TOKEN_KEY)

    override suspend fun removeSecretToken() =
        keyValueStore.remove(ENCRYPTED_TOKEN_DATA_STORE_KEY)

    override suspend fun removeUser() {
        userDataStore.updateData { UserPreferences() }
    }
}