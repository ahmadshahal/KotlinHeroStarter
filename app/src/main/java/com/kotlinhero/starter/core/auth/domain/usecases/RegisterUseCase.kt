package com.kotlinhero.starter.core.auth.domain.usecases

import com.kotlinhero.starter.core.auth.data.SessionManager
import com.kotlinhero.starter.core.auth.domain.entities.RegisterCredentials
import org.koin.core.annotation.Factory

@Factory
class RegisterUseCase(private val sessionManager: SessionManager) {
    suspend operator fun invoke(registerCredentials: RegisterCredentials) =
        sessionManager.register(registerCredentials)
}