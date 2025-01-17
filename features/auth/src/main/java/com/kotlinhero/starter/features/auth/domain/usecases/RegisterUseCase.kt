package com.kotlinhero.starter.features.auth.domain.usecases

import com.kotlinhero.starter.features.auth.data.SessionManager
import com.kotlinhero.starter.features.auth.domain.entities.RegisterCredentials
import org.koin.core.annotation.Factory

@Factory
class RegisterUseCase(private val sessionManager: SessionManager) {
    suspend operator fun invoke(registerCredentials: RegisterCredentials) =
        sessionManager.register(registerCredentials)
}