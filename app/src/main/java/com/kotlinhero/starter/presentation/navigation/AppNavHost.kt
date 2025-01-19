package com.kotlinhero.starter.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kotlinhero.starter.features.auth.presentation.navigation.authNav
import com.kotlinhero.starter.features.settings.presentation.navigation.settingsNav
import com.kotlinhero.starter.presentation.screens.MainScreen
import kotlinx.serialization.Serializable

sealed class AppDestinations {
    @Serializable
    data object MainScreen : AppDestinations()
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    startDestination: Any,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        authNav(
            navController = navController,
            onAuthentication = {
                navController.navigate(AppDestinations.MainScreen) {
                    popUpTo(0)
                }
            }
        )
        settingsNav(navController = navController)
        composable<AppDestinations.MainScreen> {
            MainScreen(navController = navController)
        }
    }
}