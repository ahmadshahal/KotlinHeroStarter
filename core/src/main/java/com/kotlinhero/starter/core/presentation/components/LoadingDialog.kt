package com.kotlinhero.starter.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.kotlinhero.starter.core.presentation.components.pulse.PulsingLoadingIndicator
import com.kotlinhero.starter.core.presentation.theme.starterColors
import com.kotlinhero.starter.core.presentation.theme.starterTypography
import com.kotlinhero.starter.res.R

@Composable
fun LoadingDialog(
    title: String = stringResource(R.string.loading),
    subtitle: String? = stringResource(R.string.content_is_now_coming_your_way)
) {
    NormalDialog(
        onDismissRequest = { },
        icon = {
            PulsingLoadingIndicator()
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
