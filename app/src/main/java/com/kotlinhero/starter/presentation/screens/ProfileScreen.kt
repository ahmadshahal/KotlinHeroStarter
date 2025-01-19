package com.kotlinhero.starter.presentation.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.kotlinhero.starter.core.presentation.components.ErrorDialog
import com.kotlinhero.starter.core.presentation.components.LoadingDialog
import com.kotlinhero.starter.core.presentation.components.LogoutDialog
import com.kotlinhero.starter.core.presentation.reusables.buttons.NormalOutlinedButton
import com.kotlinhero.starter.core.presentation.theme.starterColors
import com.kotlinhero.starter.core.presentation.theme.starterTypography
import com.kotlinhero.starter.features.settings.presentation.navigation.SettingsDestinations
import com.kotlinhero.starter.presentation.components.ProfileHeader
import com.kotlinhero.starter.presentation.components.ProfileItem
import com.kotlinhero.starter.presentation.viewmodels.ProfileViewModel
import com.kotlinhero.starter.res.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    onLogout: () -> Unit,
) {
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
                onLogout()
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
            text = stringResource(R.string.general_settings),
            style = MaterialTheme.starterTypography.body12SemiBold.copy(
                color = MaterialTheme.starterColors.neutrals500
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProfileItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_call),
                    contentDescription = null
                )
            },
            title = stringResource(R.string.phone_number),
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
            title = stringResource(R.string.payment_method),
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
            title = stringResource(R.string.ratings),
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
            title = stringResource(R.string.change_password),
            onClick = { }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.support),
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
            title = stringResource(R.string.language),
            onClick = {
                navController.navigate(SettingsDestinations.Language)
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        ProfileItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_faq),
                    contentDescription = null
                )
            },
            title = stringResource(R.string.faq),
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
            title = stringResource(R.string.privacy_policy),
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
            title = stringResource(R.string.contact_us),
            onClick = { }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.danger_zone),
            style = MaterialTheme.starterTypography.body12SemiBold.copy(
                color = MaterialTheme.starterColors.neutrals500
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        NormalOutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.logout),
            onClick = { isLogoutDialogVisible = true },
            borderColor = MaterialTheme.starterColors.error,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.starterColors.error,
            )
        )
    }
}