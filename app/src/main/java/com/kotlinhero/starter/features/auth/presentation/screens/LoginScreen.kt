package com.kotlinhero.starter.features.auth.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kotlinhero.starter.R
import com.kotlinhero.starter.core.foundation.presentation.components.ErrorDialog
import com.kotlinhero.starter.core.foundation.presentation.components.LoadingDialog
import com.kotlinhero.starter.core.foundation.presentation.reusables.buttons.NormalButton
import com.kotlinhero.starter.core.foundation.presentation.reusables.textfields.NormalTextField
import com.kotlinhero.starter.features.auth.presentation.viewmodels.LoginViewModel
import com.kotlinhero.starter.ui.theme.starterColors
import com.kotlinhero.starter.ui.theme.starterTypography
import org.koin.androidx.compose.koinViewModel

class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: LoginViewModel = koinViewModel()
        val navigator = LocalNavigator.currentOrThrow

        val state by viewModel.state.collectAsStateWithLifecycle()

        if (state.loginFetchState.isLoading) {
            LoadingDialog()
        }

        if (state.loginFetchState.isError) {
            ErrorDialog(
                onDismissRequest = viewModel::resetFetchState,
                subtitle = state.loginFetchState.failureOrNull?.getHumanReadableMessage() ?: ""
            )
        }

        Scaffold(
            containerColor = MaterialTheme.starterColors.background,
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 16.dp
                    )
                    .padding(paddingValues = innerPadding),
            ) {
                Spacer(modifier = Modifier.height(70.dp))
                Text(
                    text = "Login to your\naccount",
                    style = MaterialTheme.starterTypography.displayThree.copy(
                        color = MaterialTheme.starterColors.baseBlack
                    )
                )
                Spacer(modifier = Modifier.height(32.dp))
                NormalTextField(
                    value = state.email,
                    onValueChange = viewModel::onEmailChange,
                    hint = "Email",
                    prefix = { isFocused ->
                        val color =
                            if (isFocused) MaterialTheme.starterColors.neutrals500 else MaterialTheme.starterColors.neutrals200
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.ic_mail),
                            tint = color,
                            contentDescription = null,
                        )
                    },
                    isError = state.emailValidationState.isInvalid,
                    errorMessage = state.emailValidationState.errorMessageOrNull
                )
                Spacer(modifier = Modifier.height(12.dp))
                NormalTextField(
                    value = state.password,
                    onValueChange = viewModel::onPasswordChange,
                    hint = "Password",
                    prefix = { isFocused ->
                        val color =
                            if (isFocused) MaterialTheme.starterColors.neutrals500 else MaterialTheme.starterColors.neutrals200
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(R.drawable.ic_lock),
                            tint = color,
                            contentDescription = null,
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                )
                Spacer(modifier = Modifier.height(16.dp))
                val interactionSource = remember { MutableInteractionSource() }
                Text(
                    text = "Forgot Password?",
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = {},
                        ),
                    style = MaterialTheme.starterTypography.body12Medium.copy(
                        color = MaterialTheme.starterColors.primary
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                NormalButton(
                    text = "Login",
                    onClick = viewModel::login,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}