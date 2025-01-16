package com.kotlinhero.starter.feature.auth.domain.usecases

import com.kotlinhero.starter.feature.auth.data.SessionManager
import org.koin.core.annotation.Factory

@Factory
class LogoutUseCase(private val sessionManager: SessionManager) {
    suspend operator fun invoke() = sessionManager.logout()
}