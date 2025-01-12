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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.kotlinhero.starter.R
import com.kotlinhero.starter.app.presentation.components.ProfileItem
import com.kotlinhero.starter.app.presentation.theme.starterColors
import com.kotlinhero.starter.app.presentation.theme.starterTypography
import com.kotlinhero.starter.core.foundation.presentation.reusables.buttons.NormalOutlinedButton

class ProfileScreen : Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.starterColors.background)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues = PaddingValues(bottom = 140.dp, top = 48.dp))
                .padding(horizontal = 16.dp)
        ) {
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
                        painter = painterResource(id = R.drawable.ic_lock),
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
                onClick = {}
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
                onClick = {}
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
                onClick = {},
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.starterColors.error,
                    containerColor = Color.Transparent,
                )
            )
        }
    }
}