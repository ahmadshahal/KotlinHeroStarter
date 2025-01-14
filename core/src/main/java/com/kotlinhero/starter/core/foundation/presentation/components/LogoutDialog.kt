package com.kotlinhero.starter.core.foundation.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.kotlinhero.starter.core.foundation.presentation.components.pulse.PulsingCircle
import com.kotlinhero.starter.core.foundation.presentation.reusables.buttons.NormalButton
import com.kotlinhero.starter.core.foundation.presentation.reusables.buttons.NormalOutlinedButton
import com.kotlinhero.starter.core.foundation.presentation.theme.starterColors
import com.kotlinhero.starter.core.foundation.presentation.theme.starterTypography
import com.kotlinhero.starter.res.R

@Composable
fun LogoutDialog(
    onLogoutClicked: () -> Unit,
    onDismissRequest: () -> Unit
) {
    NormalDialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        icon = {
            Box(contentAlignment = Alignment.Center) {
                PulsingCircle(color = MaterialTheme.starterColors.error)
                Icon(
                    painter = painterResource(id = R.drawable.ic_logout),
                    contentDescription = null,
                    tint = MaterialTheme.starterColors.white
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.are_you_sure_you_want_to_logout),
                textAlign = TextAlign.Center,
                style = MaterialTheme.starterTypography.headingFour
            )
        },
        subtitle = {
            Text(
                text = stringResource(R.string.you_will_have_to_enter_your_credentials_again),
                textAlign = TextAlign.Center,
                style = MaterialTheme.starterTypography.body12Medium.copy(
                    color = MaterialTheme.starterColors.neutrals500
                )
            )
        },
        buttons = {
            NormalOutlinedButton(
                modifier = Modifier.weight(1F),
                text = stringResource(R.string.cancel),
                onClick = onDismissRequest,
                borderColor = MaterialTheme.starterColors.primary,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.starterColors.primary
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            NormalButton(
                modifier = Modifier.weight(1F),
                text = stringResource(R.string.logout),
                onClick = onLogoutClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.starterColors.primary,
                    contentColor = MaterialTheme.starterColors.white
                )
            )
        }
    )
}
