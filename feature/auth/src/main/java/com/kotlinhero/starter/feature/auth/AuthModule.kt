package com.kotlinhero.starter.feature.auth

import cafe.adriel.voyager.core.registry.screenModule
import com.kotlinhero.starter.feature.auth.presentation.screens.BiometricLoginSetupScreen
import com.kotlinhero.starter.feature.auth.presentation.screens.LoginScreen
import com.kotlinhero.starter.feature.auth.presentation.screens.RegisterScreen
import com.kotlinhero.starter.navigation.KtHeroScreen
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan
class AuthModule

val AuthNavigationModule = screenModule {
    register<KtHeroScreen.LoginScreen> { LoginScreen() }
    register<KtHeroScreen.RegisterScreen> { RegisterScreen() }
    register<KtHeroScreen.BiometricLoginSetupScreen> { BiometricLoginSetupScreen() }
}