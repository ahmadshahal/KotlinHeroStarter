package com.kotlinhero.starter.settings

import cafe.adriel.voyager.core.registry.screenModule
import com.kotlinhero.starter.navigation.KtHeroScreen
import com.kotlinhero.starter.settings.presentation.screens.LanguageScreen
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan
class SettingsModule

val SettingsNavigationModule = screenModule {
    register<KtHeroScreen.LanguageScreen> { LanguageScreen() }
}