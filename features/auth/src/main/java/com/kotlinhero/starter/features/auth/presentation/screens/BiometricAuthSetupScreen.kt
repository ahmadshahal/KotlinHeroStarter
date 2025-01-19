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
import androidx.navigation.NavController
import com.kotlinhero.starter.core.presentation.components.ErrorDialog
import com.kotlinhero.starter.core.presentation.components.SetupBiometricsLoadingDialog
import com.kotlinhero.starter.core.presentation.reusables.buttons.NormalButton
import com.kotlinhero.starter.core.presentation.reusables.textfields.NormalTextField
import com.kotlinhero.starter.core.presentation.reusables.topbar.DefaultTopBar
import com.kotlinhero.starter.core.presentation.theme.starterColors
import com.kotlinhero.starter.core.presentation.theme.starterTypography
import com.kotlinhero.starter.core.utils.getActivity
import com.kotlinhero.starter.features.auth.presentation.viewmodels.BiometricAuthSetupViewModel
import com.kotlinhero.starter.res.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BiometricAuthSetupScreen(navController: NavController) {
    val viewModel: BiometricAuthSetupViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val activity = context.getActivity() as? AppCompatActivity

    LaunchedEffect(state.setupResultState) {
        if (state.setupResultState.isSuccess) {
            navController.navigateUp()
            viewModel.resetSetupResultState()
        }
    }

    if (state.setupResultState.isLoading) {
        SetupBiometricsLoadingDialog()
    }

    if (state.setupResultState.isError) {
        ErrorDialog(
            onDismissRequest = viewModel::resetSetupResultState,
            subtitle = state.setupResultState.failureOrNull?.getHumanReadableMessage() ?: ""
        )
    }

    Scaffold(
        topBar = { DefaultTopBar(onNavigateUp = navController::navigateUp) },
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