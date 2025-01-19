package com.kotlinhero.starter.features.auth.domain.usecases

import com.kotlinhero.starter.features.auth.data.SessionManager
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
class IsAuthenticatedUseCase(private val sessionManager: SessionManager) {
    suspend operator fun invoke(): Boolean {
        val isLoggedIn = sessionManager.isLoggedInFlow.first()
        return isLoggedIn
    }
}