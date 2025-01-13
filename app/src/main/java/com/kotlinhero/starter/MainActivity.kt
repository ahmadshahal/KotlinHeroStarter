package com.kotlinhero.starter

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.kotlinhero.starter.app.presentation.theme.StarterTheme
import com.kotlinhero.starter.app.presentation.viewmodels.MainViewModel
import com.kotlinhero.starter.features.auth.presentation.screens.LoginScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
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
                    LaunchedEffect(state.startDestinationResultState) {
                        if (state.startDestinationResultState.isSuccess) {
                            val startDestination = state.startDestinationResultState.dataOrNull
                                ?: return@LaunchedEffect
                            navigator.replace(startDestination)
                            viewModel.resetStartDestinationResultState()
                        }
                    }
                    FadeTransition(navigator)
                }
            }
        }
    }
}
