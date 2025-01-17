package com.kotlinhero.starter.features.settings.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kotlinhero.starter.core.domain.enums.Language
import com.kotlinhero.starter.core.presentation.theme.starterColors
import com.kotlinhero.starter.core.presentation.theme.starterTypography
import com.kotlinhero.starter.res.R

@Composable
fun LanguageItem(
    isChecked: Boolean,
    language: Language,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(
                horizontal = 22.dp,
                vertical = 14.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = language.titleResId),
            style = MaterialTheme.starterTypography.body14Regular
        )
        Spacer(modifier = Modifier.weight(1F))
        CircularCheckbox(
            isChecked = isChecked,
            onClick = onClick
        )
    }
}

@Composable
private fun CircularCheckbox(
    isChecked: Boolean,
    onClick: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.starterColors.white,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .background(if (isChecked) MaterialTheme.starterColors.primary else MaterialTheme.starterColors.white)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            if (isChecked) {
                Image(
                    modifier = Modifier.padding(4.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_tick),
                    contentDescription = null
                )
            }
        }
    }
}
