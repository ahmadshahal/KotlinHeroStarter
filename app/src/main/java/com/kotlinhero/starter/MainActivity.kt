package com.kotlinhero.starter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import com.kotlinhero.starter.features.auth.presentation.screens.LoginScreen
import com.kotlinhero.starter.ui.theme.StarterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
            StarterTheme {
                Navigator(LoginScreen())
            }
        }
    }
}
