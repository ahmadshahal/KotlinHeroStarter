package com.kotlinhero.starter.features.settings.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kotlinhero.starter.features.settings.presentation.screens.LanguageScreen
import kotlinx.serialization.Serializable

sealed class SettingsDestinations {
    @Serializable
    data object Language : SettingsDestinations()
}

fun NavGraphBuilder.settingsNav(navController: NavController) {
    composable<SettingsDestinations.Language> {
        LanguageScreen(navController = navController)
    }
}
