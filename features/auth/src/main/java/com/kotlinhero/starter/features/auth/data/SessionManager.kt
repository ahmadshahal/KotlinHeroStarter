package com.kotlinhero.starter.features.auth.data

import com.kotlinhero.starter.core.data.repositories.BaseRepository
import com.kotlinhero.starter.core.utils.Either
import com.kotlinhero.starter.core.utils.failures.Failure
import com.kotlinhero.starter.features.auth.data.local.datasources.AuthLocalStorage
import com.kotlinhero.starter.features.auth.data.local.models.UserPreferences
import com.kotlinhero.starter.features.auth.data.mappers.toLoginCredentialsDto
import com.kotlinhero.starter.features.auth.data.mappers.toRegisterCredentialsDto
import com.kotlinhero.starter.features.auth.data.mappers.toUser
import com.kotlinhero.starter.features.auth.data.mappers.toUserPreferences
import com.kotlinhero.starter.features.auth.data.remote.api.AuthApi
import com.kotlinhero.starter.features.auth.domain.entities.AuthorizationTokens
import com.kotlinhero.starter.features.auth.domain.entities.CipherText
import com.kotlinhero.starter.features.auth.domain.entities.LoginCredentials
import com.kotlinhero.starter.features.auth.domain.entities.RegisterCredentials
import com.kotlinhero.starter.features.auth.domain.entities.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single

interface SessionManager {

    val accessToken: StateFlow<String?>

    val refreshToken: StateFlow<String?>

    val userFlow: StateFlow<User>

    val isLoggedInFlow: StateFlow<Boolean>

    val forceLogoutFlow: SharedFlow<Unit>

    suspend fun refreshToken(accessToken: String): Either<Failure, String>

    suspend fun login(loginCredentials: LoginCredentials): Either<Failure, AuthorizationTokens>

    suspend fun login(refreshToken: String)

    suspend fun getSecretToken(): CipherText?

    suspend fun saveSecretToken(cipherText: CipherText)

    suspend fun register(registerCredentials: RegisterCredentials): Either<Failure, AuthorizationTokens>

    suspend fun logout(permanently: Boolean = false): Either<Failure, Unit>

    suspend fun triggerForceLogout()
}

@Single(binds = [SessionManager::class])
internal class SessionManagerImpl(
    private val authLocalStorage: AuthLocalStorage,
    private val authApi: AuthApi,
) : SessionManager, BaseRepository() {

    private val _accessToken = MutableStateFlow<String?>(null)
    override val accessToken: StateFlow<String?> get() = _accessToken

    private val _refreshToken = MutableStateFlow<String?>(null)
    override val refreshToken: StateFlow<String?> get() = _refreshToken

    override val userFlow: StateFlow<User> = authLocalStorage
        .userFlow
        .map { it.toUser() }
        .stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.Lazily,
            initialValue = User()
        )

    private val _isLoggedInFlow = MutableStateFlow(false)
    override val isLoggedInFlow: StateFlow<Boolean> get() = _isLoggedInFlow

    private val _forceLogoutFlow = MutableSharedFlow<Unit>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val forceLogoutFlow: SharedFlow<Unit> get() = _forceLogoutFlow

    init {
        loadTokens()
    }

    private fun loadTokens() {
        _accessToken.value = authLocalStorage.getAccessToken()
        _refreshToken.value = authLocalStorage.getRefreshToken()
        _isLoggedInFlow.value = !refreshToken.value.isNullOrEmpty()
    }

    override suspend fun refreshToken(accessToken: String): Either<Failure, String> {
        // Only one coroutine is allowed to refreshToken at one time.
        return withContext(Dispatchers.IO.limitedParallelism(1)) {
            if (accessToken != _accessToken.value) {
                // Token has been refreshed by another coroutine while waiting for the semaphore.
                return@withContext Either.Right(_accessToken.value ?: "")
            }
            safeApiCall {
                // TODO: Throw an exception if 'refreshToken' was called without
                //  having a refreshToken
                val refreshToken = _refreshToken.value ?: ""
                val authorizationRefresh = authApi.refreshToken(refreshToken)
                saveAccessToken(authorizationRefresh.accessToken)
                saveRefreshToken(authorizationRefresh.refreshToken)
                authorizationRefresh.accessToken
            }
        }
    }

    override suspend fun login(loginCredentials: LoginCredentials): Either<Failure, AuthorizationTokens> {
        return safeApiCall {
            val authorizationDetails = authApi.login(loginCredentials.toLoginCredentialsDto())
            saveAccessToken(authorizationDetails.accessToken)
            saveRefreshToken(authorizationDetails.refreshToken)
            saveUser(authorizationDetails.user.toUserPreferences())
            AuthorizationTokens(
                accessToken = authorizationDetails.accessToken,
                refreshToken = authorizationDetails.refreshToken
            )
        }
    }

    override suspend fun login(refreshToken: String) {
        // This login type does not save the user locally and should be fetched from the server.
        _refreshToken.value = refreshToken

        // Commenting this will enforce the user to login each time they open the application
        // either by biometrics or by password.

        // saveRefreshToken(refreshToken)
    }

    override suspend fun getSecretToken() = authLocalStorage.getSecretToken()

    override suspend fun saveSecretToken(cipherText: CipherText) =
        authLocalStorage.setSecretToken(cipherText)

    override suspend fun register(registerCredentials: RegisterCredentials): Either<Failure, AuthorizationTokens> {
        return safeApiCall {
            val authorizationDetails =
                authApi.register(registerCredentials.toRegisterCredentialsDto())
            saveAccessToken(authorizationDetails.accessToken)
            saveRefreshToken(authorizationDetails.refreshToken)
            saveUser(authorizationDetails.user.toUserPreferences())
            AuthorizationTokens(
                accessToken = authorizationDetails.accessToken,
                refreshToken = authorizationDetails.refreshToken
            )
        }
    }

    override suspend fun logout(permanently: Boolean): Either<Failure, Unit> {
        return safeApiCall {
            // authApi.logout()
            clearSession(permanently = permanently)
        }
    }

    override suspend fun triggerForceLogout() {
        clearSession(permanently = true)
        _forceLogoutFlow.tryEmit(Unit)
    }

    private suspend fun saveAccessToken(token: String) {
        _accessToken.value = token
        authLocalStorage.setAccessToken(token)
        _isLoggedInFlow.value = true
    }

    private suspend fun saveRefreshToken(token: String) {
        _refreshToken.value = token
        authLocalStorage.setRefreshToken(token)
    }

    private suspend fun saveUser(userPreferences: UserPreferences) {
        authLocalStorage.setUser(userPreferences)
    }

    private suspend fun clearSession(permanently: Boolean) {
        _accessToken.value = null
        _refreshToken.value = null
        if (permanently) authLocalStorage.removeSecretToken()
        authLocalStorage.removeAccessToken()
        authLocalStorage.removeRefreshToken()
        authLocalStorage.removeUser()
        _isLoggedInFlow.value = false
    }
}