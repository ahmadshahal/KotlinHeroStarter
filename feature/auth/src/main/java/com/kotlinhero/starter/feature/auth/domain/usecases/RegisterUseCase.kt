package com.kotlinhero.starter.feature.auth.domain.usecases

import com.kotlinhero.starter.feature.auth.data.SessionManager
import com.kotlinhero.starter.feature.auth.domain.entities.RegisterCredentials
import org.koin.core.annotation.Factory

@Factory
class RegisterUseCase(private val sessionManager: SessionManager) {
    suspend operator fun invoke(registerCredentials: RegisterCredentials) =
        sessionManager.register(registerCredentials)
}