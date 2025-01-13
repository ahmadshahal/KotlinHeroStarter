package com.kotlinhero.starter.features.auth.presentation.screens

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kotlinhero.starter.R
import com.kotlinhero.starter.app.presentation.screens.MainScreen
import com.kotlinhero.starter.app.presentation.theme.starterColors
import com.kotlinhero.starter.app.presentation.theme.starterTypography
import com.kotlinhero.starter.core.foundation.presentation.components.ErrorDialog
import com.kotlinhero.starter.core.foundation.presentation.components.LoadingDialog
import com.kotlinhero.starter.core.foundation.presentation.reusables.buttons.NormalButton
import com.kotlinhero.starter.core.foundation.presentation.reusables.buttons.NormalOutlinedButton
import com.kotlinhero.starter.core.foundation.presentation.reusables.textfields.NormalTextField
import com.kotlinhero.starter.core.foundation.presentation.reusables.topbar.DefaultTopBar
import com.kotlinhero.starter.core.foundation.utils.getActivity
import com.kotlinhero.starter.features.auth.presentation.viewmodels.LoginViewModel
import org.koin.androidx.compose.koinViewModel

class LoginScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: LoginViewModel = koinViewModel()
        val navigator = LocalNavigator.currentOrThrow

        val state by viewModel.state.collectAsStateWithLifecycle()

        val context = LocalContext.current
        val activity = context.getActivity() as? AppCompatActivity

        LaunchedEffect(state.biometricResultState) {
            when {
                state.biometricResultState.isRequiresSetup -> {
                    viewModel.resetBiometricLoginResultState()
                    navigator.push(BiometricLoginSetupScreen())
                }
                state.biometricResultState.isSuccess -> {
                    navigator.replace(MainScreen())
                    viewModel.resetBiometricLoginResultState()
                }
            }
        }

        LaunchedEffect(state.loginResultState) {
            when {
                state.loginResultState.isSuccess -> {
                    viewModel.resetLoginResultState()
                    navigator.replace(MainScreen())
                }
            }
        }

        if (state.loginResultState.isLoading) {
            LoadingDialog()
        }

        if (state.loginResultState.isError) {
            ErrorDialog(
                onDismissRequest = viewModel::resetLoginResultState,
                subtitle = state.loginResultState.failureOrNull?.getHumanReadableMessage() ?: ""
            )
        }

        Scaffold(
            topBar = { DefaultTopBar(showBackButton = false) },
            containerColor = MaterialTheme.starterColors.background,
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(paddingValues = innerPadding)
                    .verticalScroll(rememberScrollState()),
            ) {
                Spacer(modifier = Modifier.height(45.dp))
                Text(
                    text = stringResource(R.string.login_to_your_account),
                    style = MaterialTheme.starterTypography.displayThree.copy(
                        color = MaterialTheme.starterColors.baseBlack
                    )
                )
                Spacer(modifier = Modifier.height(32.dp))
                NormalTextField(
                    value = state.email,
                    onValueChange = viewModel::onEmailChange,
                    hint = stringResource(R.string.email),
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
                    hint = stringResource(R.string.password),
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
                Spacer(modifier = Modifier.height(32.dp))
                NormalButton(
                    text = stringResource(R.string.login),
                    onClick = viewModel::login,
                    modifier = Modifier.fillMaxWidth()
                )
                if(state.isBiometricAvailable) {
                    Spacer(modifier = Modifier.height(16.dp))
                    NormalOutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.biometric_login),
                        onClick = { viewModel.biometricLogin(activity) }
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.don_t_have_an_account),
                        style = MaterialTheme.starterTypography.body12Regular.copy(
                            color = MaterialTheme.starterColors.neutrals500
                        )
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = stringResource(R.string.register),
                        style = MaterialTheme.starterTypography.body12Regular.copy(
                            color = MaterialTheme.starterColors.primary
                        ),
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                navigator.push(RegisterScreen())
                            },
                        )
                    )
                }
            }
        }
    }
}