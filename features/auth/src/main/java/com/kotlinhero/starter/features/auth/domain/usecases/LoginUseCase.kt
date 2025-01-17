package com.kotlinhero.starter.features.auth.domain.usecases

import com.kotlinhero.starter.features.auth.data.SessionManager
import com.kotlinhero.starter.features.auth.domain.entities.LoginCredentials
import org.koin.core.annotation.Factory

@Factory
class LoginUseCase(private val sessionManager: SessionManager) {
    suspend operator fun invoke(loginCredentials: LoginCredentials) =
        sessionManager.login(loginCredentials)
}