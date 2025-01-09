package com.kotlinhero.starter.core.foundation.presentation.components.pulse

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kotlinhero.starter.ui.theme.starterColors

@Composable
fun PulsingCircle(color: Color = MaterialTheme.starterColors.primary) {
    val infiniteTransition = rememberInfiniteTransition("")

    val scale1 by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )
    val scale2 by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, delayMillis = 300, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )
    val scale3 by infiniteTransition.animateFloat(
        initialValue = 1.2f,
        targetValue = 1.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, delayMillis = 300, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Box(
        modifier = Modifier.size(160.dp),
        contentAlignment = Alignment.Center
    ) {
        Circle(scale = scale3, color = color.copy(alpha = 0.1f), size = 150.dp)
        Circle(scale = scale2, color = color.copy(alpha = 0.3f), size = 140.dp)
        Circle(scale = scale1, color = color.copy(alpha = 1f), size = 100.dp)
    }
}

@Composable
private fun Circle(scale: Float, color: Color, size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .scale(scale)
            .background(color, shape = CircleShape)
    )
}

@Preview(showBackground = true)
@Composable
private fun PulsingCirclePreview() {
    MaterialTheme {
        PulsingCircle()
    }
}
