package com.kotlinhero.starter.feature.auth.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.kotlinhero.starter.core.foundation.presentation.components.ErrorDialog
import com.kotlinhero.starter.core.foundation.presentation.components.LoadingDialog
import com.kotlinhero.starter.core.foundation.presentation.reusables.buttons.NormalButton
import com.kotlinhero.starter.core.foundation.presentation.reusables.textfields.NormalTextField
import com.kotlinhero.starter.core.foundation.presentation.reusables.topbar.DefaultTopBar
import com.kotlinhero.starter.core.foundation.presentation.theme.starterColors
import com.kotlinhero.starter.core.foundation.presentation.theme.starterTypography
import com.kotlinhero.starter.core.foundation.utils.voyager.transitions.SlideTransition
import com.kotlinhero.starter.feature.auth.presentation.viewmodels.RegisterViewModel
import com.kotlinhero.starter.res.R
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalVoyagerApi::class)
class RegisterScreen(
    private val onRegistration: () -> Unit = {}
) : Screen, ScreenTransition by SlideTransition() {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: RegisterViewModel = koinViewModel()
        val navigator = LocalNavigator.currentOrThrow

        val state by viewModel.state.collectAsStateWithLifecycle()

        if (state.registerResultState.isLoading) {
            LoadingDialog()
        }

        if (state.registerResultState.isError) {
            ErrorDialog(
                onDismissRequest = viewModel::resetRegisterResultState,
                subtitle = state.registerResultState.failureOrNull?.getHumanReadableMessage() ?: ""
            )
        }


        LaunchedEffect(state.registerResultState) {
            when {
                state.registerResultState.isSuccess -> {
                    viewModel.resetRegisterResultState()
                    onRegistration()
                }
            }
        }

        Scaffold(
            topBar = { DefaultTopBar(onNavigateUp = navigator::pop) },
            containerColor = MaterialTheme.starterColors.background,
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(paddingValues = innerPadding),
            ) {
                Spacer(modifier = Modifier.height(45.dp))
                Text(
                    text = stringResource(R.string.create_a_new_account),
                    style = MaterialTheme.starterTypography.displayThree.copy(
                        color = MaterialTheme.starterColors.baseBlack
                    )
                )
                Spacer(modifier = Modifier.height(32.dp))
                NormalTextField(
                    value = state.fullName,
                    onValueChange = viewModel::onFullNameChange,
                    hint = stringResource(R.string.full_name),
                    prefix = { isFocused ->
                        val color =
                            if (isFocused) MaterialTheme.starterColors.neutrals500 else MaterialTheme.starterColors.neutrals200
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(R.drawable.ic_user),
                            tint = color,
                            contentDescription = null,
                        )
                    },
                    isError = state.fullNameValidationState.isInvalid,
                    errorMessage = state.fullNameValidationState.errorMessageOrNull
                )
                Spacer(modifier = Modifier.height(12.dp))
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
                    isError = state.passwordValidationState.isInvalid,
                    errorMessage = state.passwordValidationState.errorMessageOrNull,
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
                    text = stringResource(R.string.register),
                    onClick = viewModel::register,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}