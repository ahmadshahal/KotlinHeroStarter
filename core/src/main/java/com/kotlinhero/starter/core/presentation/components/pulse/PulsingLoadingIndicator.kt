package com.kotlinhero.starter.core.presentation.components.pulse

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import com.kotlinhero.starter.core.presentation.theme.starterColors

@Composable
fun PulsingLoadingIndicator(color: Color = MaterialTheme.starterColors.secondary) {
    Box(contentAlignment = Alignment.Center) {
        PulsingCircle(color = color)
        CircularProgressIndicator(
            color = MaterialTheme.starterColors.white,
            strokeCap = StrokeCap.Round
        )
    }
}
