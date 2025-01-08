package com.kotlinhero.starter.core.auth.data

import com.kotlinhero.starter.core.auth.data.local.datasources.AuthLocalStorage
import com.kotlinhero.starter.core.auth.data.local.models.UserPreferences
import com.kotlinhero.starter.core.auth.data.mappers.toLoginCredentialsDto
import com.kotlinhero.starter.core.auth.data.mappers.toRegisterCredentialsDto
import com.kotlinhero.starter.core.auth.data.mappers.toUserPreferences
import com.kotlinhero.starter.core.foundation.data.repositories.BaseRepository
import com.kotlinhero.starter.core.foundation.utils.Either
import com.kotlinhero.starter.core.foundation.utils.failures.Failure
import com.kotlinhero.starter.core.auth.data.remote.api.AuthApi
import com.kotlinhero.starter.core.auth.domain.entities.LoginCredentials
import com.kotlinhero.starter.core.auth.domain.entities.RegisterCredentials
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.koin.core.annotation.Single

interface SessionManager {

    val accessToken: StateFlow<String?>

    val refreshToken: StateFlow<String?>

    val userFlow: StateFlow<UserPreferences?>

    val isLoggedInFlow: StateFlow<Boolean>

    val forceLogoutFlow: SharedFlow<Unit>

    suspend fun refreshToken(accessToken: String): Either<Failure, String>

    suspend fun login(loginCredentials: LoginCredentials): Either<Failure, Unit>

    suspend fun register(registerCredentials: RegisterCredentials): Either<Failure, Unit>

    suspend fun logout(): Either<Failure, Unit>

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

    private val _userFlow = authLocalStorage.userFlow.stateIn(
        scope = CoroutineScope(Dispatchers.Default),
        started = SharingStarted.Lazily,
        initialValue = null
    )
    override val userFlow: StateFlow<UserPreferences?> get() = _userFlow

    private val _isLoggedInFlow = MutableStateFlow(false)
    override val isLoggedInFlow: StateFlow<Boolean> get() = _isLoggedInFlow

    private val _forceLogoutFlow = MutableSharedFlow<Unit>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val forceLogoutFlow: SharedFlow<Unit> get() = _forceLogoutFlow

    init {
        runBlocking {
            loadTokens()
        }
    }

    private suspend fun loadTokens() {
        _accessToken.value = authLocalStorage.getAccessToken()
        _refreshToken.value = authLocalStorage.getRefreshToken()
        _isLoggedInFlow.value = !accessToken.value.isNullOrEmpty()
    }

    override suspend fun refreshToken(accessToken: String): Either<Failure, String> {
        // Only one coroutine is allowed to refreshToken at one time.
        return withContext(Dispatchers.IO.limitedParallelism(1)) {
            if(accessToken != _accessToken.value) {
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

    override suspend fun login(loginCredentials: LoginCredentials): Either<Failure, Unit> {
        return safeApiCall {
            val authorizationDetails = authApi.login(loginCredentials.toLoginCredentialsDto())
            saveAccessToken(authorizationDetails.accessToken)
            saveRefreshToken(authorizationDetails.refreshToken)
            saveUser(authorizationDetails.user.toUserPreferences())
        }
    }

    override suspend fun register(registerCredentials: RegisterCredentials): Either<Failure, Unit> {
        return safeApiCall {
            val authorizationDetails =
                authApi.register(registerCredentials.toRegisterCredentialsDto())
            saveAccessToken(authorizationDetails.accessToken)
            saveRefreshToken(authorizationDetails.refreshToken)
            saveUser(authorizationDetails.user.toUserPreferences())
        }
    }

    override suspend fun logout(): Either<Failure, Unit> {
        return safeApiCall {
            authApi.logout()
            clearSession()
        }
    }

    override suspend fun triggerForceLogout() {
        clearSession()
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

    private suspend fun clearSession() {
        _accessToken.value = null
        _refreshToken.value = null
        authLocalStorage.removeAccessToken()
        authLocalStorage.removeRefreshToken()
        authLocalStorage.removeUser()
        _isLoggedInFlow.value = false
    }
}