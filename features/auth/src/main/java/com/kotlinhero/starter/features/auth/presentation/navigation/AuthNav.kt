package com.kotlinhero.starter.features.auth.presentation.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kotlinhero.starter.features.auth.presentation.navigation.AuthDestinations.Auth
import com.kotlinhero.starter.features.auth.presentation.navigation.AuthDestinations.BiometricAuthSetup
import com.kotlinhero.starter.features.auth.presentation.navigation.AuthDestinations.Login
import com.kotlinhero.starter.features.auth.presentation.navigation.AuthDestinations.Register
import com.kotlinhero.starter.features.auth.presentation.screens.BiometricAuthSetupScreen
import com.kotlinhero.starter.features.auth.presentation.screens.LoginScreen
import com.kotlinhero.starter.features.auth.presentation.screens.RegisterScreen
import kotlinx.serialization.Serializable

sealed class AuthDestinations {
    @Serializable
    data object Auth : AuthDestinations()

    @Serializable
    internal data object Login : AuthDestinations()

    @Serializable
    internal data object Register : AuthDestinations()

    @Serializable
    internal data object BiometricAuthSetup : AuthDestinations()
}

fun NavGraphBuilder.authNav(
    navController: NavController,
    onAuthentication: () -> Unit,
) {
    navigation<Auth>(startDestination = Login) {
        composable<Login> {
            LoginScreen(
                onAuthentication = onAuthentication,
                navController = navController
            )
        }
        composable<Register>(
            enterTransition = { slideInHorizontally { it } },
            exitTransition = { slideOutHorizontally { it } }
        ) {
            RegisterScreen(
                onAuthentication = onAuthentication,
                navController = navController
            )
        }
        composable<BiometricAuthSetup>(
            enterTransition = { slideInHorizontally { it } },
            exitTransition = { slideOutHorizontally { it } }
        ) {
            BiometricAuthSetupScreen(
                navController = navController
            )
        }
    }
}
