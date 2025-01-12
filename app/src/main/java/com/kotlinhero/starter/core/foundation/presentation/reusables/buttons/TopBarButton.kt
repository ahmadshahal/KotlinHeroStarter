package com.kotlinhero.starter.core.foundation.presentation.reusables.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.kotlinhero.starter.app.presentation.theme.starterColors

@Composable
fun TopBarButton(
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable BoxScope.() -> Unit = {}
) {
    val alpha = if (enabled) 1F else 0.5F
    Box(
        modifier = modifier
            .alpha(alpha = alpha)
            .size(46.dp)
            .clip(CircleShape)
            .background(MaterialTheme.starterColors.white)
            .clickable(onClick = onClicked, enabled = enabled),
        contentAlignment = Alignment.Center,
        content = content
    )
}
