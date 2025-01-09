package com.kotlinhero.starter.core.foundation.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.kotlinhero.starter.R
import com.kotlinhero.starter.core.foundation.presentation.components.pulse.PulsingCircle
import com.kotlinhero.starter.core.foundation.presentation.reusables.buttons.NormalButton
import com.kotlinhero.starter.core.foundation.presentation.reusables.buttons.NormalOutlinedButton
import com.kotlinhero.starter.ui.theme.starterColors
import com.kotlinhero.starter.ui.theme.starterTypography

@Composable
fun TryAgainErrorDialog(
    onClickTryAgain: () -> Unit,
    onClickBack: () -> Unit,
    title: String = "Something went wrong",
    subtitle: String = "Please try again..",
    onDismissRequest: () -> Unit = {}
) {
    ErrorDialog(
        title = title,
        subtitle = subtitle,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        onDismissRequest = onDismissRequest,
        buttons = {
            NormalOutlinedButton(
                modifier = Modifier.weight(1F),
                text = "Go back",
                onClick = onClickBack,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.starterColors.primary
                )
            )
            Spacer(modifier = Modifier.width(10.dp))
            NormalButton(
                modifier = Modifier.weight(1F),
                text = "Try again",
                onClick = onClickTryAgain,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.starterColors.primary
                )
            )
        }
    )
}

@Composable
fun ErrorDialog(
    onDismissRequest: () -> Unit,
    title: String? = "Something went wrong",
    subtitle: String? = null,
    buttons: (@Composable RowScope.() -> Unit)? = {
        NormalButton(
            modifier = Modifier.weight(1F),
            text = "Close",
            onClick = onDismissRequest
        )
    },
    properties: DialogProperties = DialogProperties(
        dismissOnBackPress = true,
        dismissOnClickOutside = true
    )
) {
    NormalDialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
        icon = {
            Box(contentAlignment = Alignment.Center) {
                PulsingCircle(color = MaterialTheme.starterColors.error)
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_x),
                    contentDescription = null,
                    tint = MaterialTheme.starterColors.white
                )
            }
        },
        title = title?.let {
            {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.starterTypography.headingFour
                )
            }
        },
        subtitle = subtitle?.let {
            {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.starterTypography.body12Medium.copy(
                        color = MaterialTheme.starterColors.neutrals500
                    )
                )
            }
        },
        buttons = buttons
    )
}
