package com.kotlinhero.starter.core.presentation.reusables.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kotlinhero.starter.core.presentation.reusables.buttons.BackButton
import com.kotlinhero.starter.core.presentation.theme.starterColors
import com.kotlinhero.starter.core.presentation.theme.starterTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    onNavigateUp: () -> Unit = {},
    showBackButton: Boolean = true,
    backButton: @Composable () -> Unit = {
        BackButton(onClicked = onNavigateUp)
    },
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MaterialTheme.starterColors.background,
        titleContentColor = MaterialTheme.starterColors.darkText
    ),
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.starterTypography.bodySemiBold
                )
            }
        },
        navigationIcon = {
            if (showBackButton) {
                Box(modifier = Modifier.padding(start = 16.dp)) {
                    backButton()
                }
            }
        },
        actions = actions,
        colors = colors
    )
}
