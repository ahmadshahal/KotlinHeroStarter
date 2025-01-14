package com.kotlinhero.starter

import cafe.adriel.voyager.core.registry.screenModule
import com.kotlinhero.starter.navigation.KtHeroScreen
import com.kotlinhero.starter.presentation.screens.HomeScreen
import com.kotlinhero.starter.presentation.screens.MainScreen
import com.kotlinhero.starter.presentation.screens.ProfileScreen
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan
class AppModule

val AppNavigationModule = screenModule {
    register<KtHeroScreen.MainScreen> { MainScreen() }
    register<KtHeroScreen.HomeScreen> { HomeScreen() }
    register<KtHeroScreen.ProfileScreen> { ProfileScreen() }
}