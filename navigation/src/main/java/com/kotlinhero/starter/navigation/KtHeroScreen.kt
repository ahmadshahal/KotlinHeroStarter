package com.kotlinhero.starter.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class KtHeroScreen : ScreenProvider {
    data object LoginScreen : KtHeroScreen()
    data object RegisterScreen : KtHeroScreen()
    data object BiometricLoginSetupScreen : KtHeroScreen()
    data object MainScreen : KtHeroScreen()
    data object HomeScreen : KtHeroScreen()
    data object ProfileScreen : KtHeroScreen()
    data object LanguageScreen : KtHeroScreen()
}