package com.kotlinhero.starter.features.auth.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kotlinhero.starter.features.auth.presentation.viewmodels.LoginViewModel
import org.koin.androidx.compose.koinViewModel

class LoginScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: LoginViewModel = koinViewModel()
        val navigator = LocalNavigator.currentOrThrow

        val state by viewModel.state.collectAsStateWithLifecycle()

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("PlanA Starter")
                    },
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues = innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                OutlinedTextField(
                    value = state.email,
                    onValueChange = viewModel::onEmailChange
                )
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    value = state.password,
                    onValueChange = viewModel::onPasswordChange
                )
                Spacer(modifier = Modifier.height(48.dp))
                ElevatedButton(onClick = viewModel::login) {
                    when(state.loginFetchState.isLoading) {
                        true -> CircularProgressIndicator()
                        false -> Text("Login")
                    }
                }
            }
        }
    }
}