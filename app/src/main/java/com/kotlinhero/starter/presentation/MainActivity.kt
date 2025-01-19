package com.kotlinhero.starter.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.kotlinhero.starter.core.presentation.theme.StarterTheme
import com.kotlinhero.starter.features.auth.presentation.navigation.AuthDestinations
import com.kotlinhero.starter.presentation.navigation.AppDestinations
import com.kotlinhero.starter.presentation.navigation.AppNavHost
import com.kotlinhero.starter.presentation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            viewModel.showSplashScreen
        }

        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )

        setContent {
            StarterTheme {
                val navController = rememberNavController()

                val state by viewModel.state.collectAsStateWithLifecycle()
                LaunchedEffect(state.isAuthenticated) {
                    if (!state.isAuthenticated) {
                        navController.navigate(AuthDestinations.Auth) {
                            popUpTo(0)
                        }
                        viewModel.resetAuthenticationState()
                    }
                }

                AppNavHost(
                    navController = navController,
                    startDestination = AppDestinations.MainScreen
                )
            }
        }
    }
}
