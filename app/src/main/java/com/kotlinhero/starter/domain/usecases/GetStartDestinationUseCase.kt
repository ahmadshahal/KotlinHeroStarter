package com.kotlinhero.starter.domain.usecases

import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import com.kotlinhero.starter.core.auth.data.SessionManager
import com.kotlinhero.starter.navigation.KtHeroScreen
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Factory

@Factory
class GetStartDestinationUseCase(
    private val sessionManager: SessionManager,
) {
    suspend operator fun invoke(): Screen {
        val isLoggedIn = sessionManager.isLoggedInFlow.first()
        return when(isLoggedIn) {
            true -> ScreenRegistry.get(KtHeroScreen.MainScreen)
            false -> ScreenRegistry.get(KtHeroScreen.LoginScreen)
        }
    }
}