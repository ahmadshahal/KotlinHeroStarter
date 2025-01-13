package com.kotlinhero.starter.core.foundation.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.kotlinhero.starter.R
import com.kotlinhero.starter.app.presentation.theme.starterColors
import com.kotlinhero.starter.app.presentation.theme.starterTypography
import com.kotlinhero.starter.core.foundation.presentation.components.pulse.PulsingLoadingIndicator

@Composable
fun SetupBiometricsLoadingDialog(
    title: String = stringResource(R.string.authenticating_biometrics),
    subtitle: String? = stringResource(R.string.working_on_authenticating_you_by_biometrics)
) {
    NormalDialog(
        onDismissRequest = { },
        icon = {
            PulsingLoadingIndicator(color = MaterialTheme.starterColors.secondary)
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
