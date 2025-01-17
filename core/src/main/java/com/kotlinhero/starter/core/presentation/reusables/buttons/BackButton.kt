package com.kotlinhero.starter.core.presentation.reusables.buttons

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.kotlinhero.starter.res.R

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClicked: () -> Unit
) {
    TopBarButton(
        modifier = modifier,
        enabled = enabled,
        onClicked = onClicked
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_left),
            contentDescription = null
        )
    }
}
