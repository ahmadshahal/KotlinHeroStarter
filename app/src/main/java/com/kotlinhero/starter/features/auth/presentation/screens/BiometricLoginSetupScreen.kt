package com.kotlinhero.starter.features.auth.presentation.screens

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.ScreenTransition
import com.kotlinhero.starter.R
import com.kotlinhero.starter.app.presentation.theme.starterColors
import com.kotlinhero.starter.app.presentation.theme.starterTypography
import com.kotlinhero.starter.core.foundation.presentation.components.ErrorDialog
import com.kotlinhero.starter.core.foundation.presentation.components.SetupBiometricsLoadingDialog
import com.kotlinhero.starter.core.foundation.presentation.reusables.buttons.NormalButton
import com.kotlinhero.starter.core.foundation.presentation.reusables.textfields.NormalTextField
import com.kotlinhero.starter.core.foundation.presentation.reusables.topbar.DefaultTopBar
import com.kotlinhero.starter.core.foundation.utils.getActivity
import com.kotlinhero.starter.core.foundation.utils.voyager.transitions.SlideTransition
import com.kotlinhero.starter.features.auth.presentation.viewmodels.BiometricLoginSetupViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalVoyagerApi::class)
class BiometricLoginSetupScreen : Screen, ScreenTransition by SlideTransition() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel: BiometricLoginSetupViewModel = koinViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        val context = LocalContext.current
        val activity = context.getActivity() as? AppCompatActivity

        LaunchedEffect(state.loginResultState) {
            if (state.loginResultState.isSuccess) {
                navigator.pop()
                viewModel.resetLoginResultState()
            }
        }

        if (state.loginResultState.isLoading) {
            SetupBiometricsLoadingDialog()
        }

        if (state.loginResultState.isError) {
            ErrorDialog(
                onDismissRequest = viewModel::resetLoginResultState,
                subtitle = state.loginResultState.failureOrNull?.getHumanReadableMessage() ?: ""
            )
        }

        Scaffold(
            topBar = { DefaultTopBar(onNavigateUp = navigator::pop) },
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
                    text = stringResource(R.string.setup_your_biometric_login),
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
                    text = stringResource(R.string.authorize),
                    onClick = { viewModel.authorize(activity = activity) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}