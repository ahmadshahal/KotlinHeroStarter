package com.kotlinhero.starter.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.kotlinhero.starter.core.presentation.theme.starterColors

@Composable
fun StarterBottomNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 10.dp)
            .shadow(
                elevation = 24.dp,
                shape = CircleShape,
                spotColor = MaterialTheme.starterColors.neutrals100
            )
            .background(color = MaterialTheme.starterColors.white, shape = CircleShape)
            .padding(horizontal = 16.dp),
        content = content
    )
}

@Composable
fun RowScope.StarterNavigationBarItem(
    selected: Boolean,
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    onClick: () -> Unit
) {
    val animatedIndicatorColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.starterColors.background else MaterialTheme.starterColors.white,
        label = ""
    )
    NavigationBarItem(
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = animatedIndicatorColor
        ),
        selected = selected,
        onClick = onClick,
        icon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = spring(
                            stiffness = Spring.StiffnessHigh,
                            visibilityThreshold = IntSize.VisibilityThreshold
                        )
                    )
                    .height(48.dp)
            ) {
                icon()
                AnimatedVisibility(visible = selected) {
                    Row {
                        Spacer(modifier = Modifier.width(10.dp))
                        label()
                    }
                }
            }
        }
    )
}

