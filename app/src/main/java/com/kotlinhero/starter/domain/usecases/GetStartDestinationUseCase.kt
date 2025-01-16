package com.kotlinhero.starter.domain.usecases

import com.kotlinhero.starter.core.auth.data.SessionManager
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

sealed class StartDestinationResult {
    data object Auth : StartDestinationResult()
    data object Home : StartDestinationResult()
    data object Onboarding : StartDestinationResult()
}

@Factory
class GetStartDestinationUseCase(
    private val sessionManager: SessionManager,
) {
    suspend operator fun invoke(): StartDestinationResult {
        val isLoggedIn = sessionManager.isLoggedInFlow.first()
        return when(isLoggedIn) {
            true -> StartDestinationResult.Home
            false -> StartDestinationResult.Auth
        }
    }
}