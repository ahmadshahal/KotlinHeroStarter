package com.kotlinhero.starter.feature.auth.domain.usecases

import com.kotlinhero.starter.feature.auth.data.SessionManager
import com.kotlinhero.starter.feature.auth.domain.entities.LoginCredentials
import org.koin.core.annotation.Factory

@Factory
class LoginUseCase(private val sessionManager: SessionManager) {
    suspend operator fun invoke(loginCredentials: LoginCredentials) =
        sessionManager.login(loginCredentials)
}