package com.kotlinhero.starter.app.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kotlinhero.starter.R
import com.kotlinhero.starter.app.presentation.components.ProfileHeader
import com.kotlinhero.starter.app.presentation.components.ProfileItem
import com.kotlinhero.starter.app.presentation.theme.starterColors
import com.kotlinhero.starter.app.presentation.theme.starterTypography
import com.kotlinhero.starter.app.presentation.viewmodels.ProfileViewModel
import com.kotlinhero.starter.core.foundation.presentation.components.ErrorDialog
import com.kotlinhero.starter.core.foundation.presentation.components.LoadingDialog
import com.kotlinhero.starter.core.foundation.presentation.components.LogoutDialog
import com.kotlinhero.starter.core.foundation.presentation.reusables.buttons.NormalOutlinedButton
import com.kotlinhero.starter.features.auth.presentation.screens.LoginScreen
import org.koin.androidx.compose.koinViewModel

class ProfileScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow.parent ?: throw IllegalStateException()

        val viewModel = koinViewModel<ProfileViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()

        var isLogoutDialogVisible by remember { mutableStateOf(false) }
        if(isLogoutDialogVisible) {
            LogoutDialog(
                onLogoutClicked = {
                    isLogoutDialogVisible = false
                    viewModel.logout()
                },
                onDismissRequest = { isLogoutDialogVisible = false }
            )
        }

        LaunchedEffect(state.logoutResultState) {
            when {
                state.logoutResultState.isSuccess -> {
                    viewModel.resetLogoutResultState()
                    navigator.replace(LoginScreen())
                }
            }
        }

        if (state.logoutResultState.isLoading) {
            LoadingDialog()
        }

        if (state.logoutResultState.isError) {
            ErrorDialog(
                onDismissRequest = viewModel::resetLogoutResultState,
                subtitle = state.logoutResultState.failureOrNull?.getHumanReadableMessage() ?: ""
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.starterColors.background)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues = PaddingValues(bottom = 140.dp, top = 48.dp))
                .padding(horizontal = 16.dp)
        ) {
            ProfileHeader(
                fullName = state.user.fullName,
                email = state.user.email
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "GENERAL SETTINGS",
                style = MaterialTheme.starterTypography.body12SemiBold.copy(
                    color = MaterialTheme.starterColors.neutrals500
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            ProfileItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_personalcard),
                        contentDescription = null
                    )
                },
                title = "ID Verification",
                onClick = { },
            )
            Spacer(modifier = Modifier.height(8.dp))
            ProfileItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_call),
                        contentDescription = null
                    )
                },
                title = "Phone Number",
                onClick = { },
            )
            Spacer(modifier = Modifier.height(8.dp))
            ProfileItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_card_pos),
                        contentDescription = null
                    )
                },
                title = "Payment Method",
                onClick = { },
            )
            Spacer(modifier = Modifier.height(8.dp))
            ProfileItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = null
                    )
                },
                title = "Ratings",
                onClick = { }
            )
            Spacer(modifier = Modifier.height(8.dp))
            ProfileItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock_outline),
                        contentDescription = null
                    )
                },
                title = "Change Password",
                onClick = { }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "SUPPORT",
                style = MaterialTheme.starterTypography.body12SemiBold.copy(
                    color = MaterialTheme.starterColors.neutrals500
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            ProfileItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_global),
                        contentDescription = null
                    )
                },
                title = "Language",
                onClick = { }
            )
            Spacer(modifier = Modifier.height(8.dp))
            ProfileItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_faq),
                        contentDescription = null
                    )
                },
                title = "FAQ",
                onClick = { }
            )
            Spacer(modifier = Modifier.height(8.dp))
            ProfileItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_headphone),
                        contentDescription = null
                    )
                },
                title = "Privacy Policy",
                onClick = { }
            )
            Spacer(modifier = Modifier.height(8.dp))
            ProfileItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_call),
                        contentDescription = null
                    )
                },
                title = "Contact Us",
                onClick = { }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "DANGER ZONE",
                style = MaterialTheme.starterTypography.body12SemiBold.copy(
                    color = MaterialTheme.starterColors.neutrals500
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            NormalOutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Logout",
                onClick = { isLogoutDialogVisible = true },
                borderColor = MaterialTheme.starterColors.error,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.starterColors.error,
                )
            )
        }
    }
}