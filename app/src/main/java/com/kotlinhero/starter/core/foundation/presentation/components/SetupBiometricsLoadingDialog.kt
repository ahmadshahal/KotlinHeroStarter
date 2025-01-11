package com.kotlinhero.starter.core.foundation.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import com.kotlinhero.starter.core.foundation.presentation.components.pulse.PulsingLoadingIndicator
import com.kotlinhero.starter.ui.theme.starterColors
import com.kotlinhero.starter.ui.theme.starterTypography

@Composable
fun SetupBiometricsLoadingDialog(
    title: String = "Authenticating Biometrics",
    subtitle: String? = "Working on authenticating you by biometrics"
) {
    NormalDialog(
        onDismissRequest = { },
        icon = {
            PulsingLoadingIndicator(color = MaterialTheme.starterColors.secondary500)
        },
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.starterTypography.headingFour
            )
        },
        subtitle = {
            subtitle?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.starterTypography.body12Medium.copy(
                        color = MaterialTheme.starterColors.neutrals500
                    )
                )
            }
        }
    )
}
