package com.kotlinhero.starter

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
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.kotlinhero.starter.core.foundation.presentation.theme.StarterTheme
import com.kotlinhero.starter.domain.usecases.StartDestinationResult
import com.kotlinhero.starter.feature.auth.presentation.screens.LoginScreen
import com.kotlinhero.starter.presentation.screens.HomeScreen
import com.kotlinhero.starter.presentation.screens.MainScreen
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
            val state by viewModel.state.collectAsStateWithLifecycle()
            StarterTheme {
                Navigator(LoginScreen()) { navigator ->
                    FadeTransition(navigator)
                    LaunchedEffect(state.startDestinationResultState) {
                        if (state.startDestinationResultState.isSuccess) {
                            val startDestinationResult =
                                state.startDestinationResultState.dataOrNull
                                    ?: return@LaunchedEffect
                            handleStartDestinationResult(startDestinationResult, navigator)
                            viewModel.resetStartDestinationResultState()
                        }
                    }
                }
            }
        }
    }

    private fun handleStartDestinationResult(
        startDestinationResult: StartDestinationResult,
        navigator: Navigator
    ) {
        val destination = when (startDestinationResult) {
            StartDestinationResult.Home -> MainScreen()
            else -> LoginScreen(onAuthentication = { navigator.replace(item = HomeScreen()) })
        }
        navigator.replace(destination)
    }
}
