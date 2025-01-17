package com.kotlinhero.starter.core.presentation.reusables.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kotlinhero.starter.core.presentation.theme.starterColors
import com.kotlinhero.starter.core.presentation.theme.starterTypography

@Composable
fun NormalOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.starterColors.primary
    ),
    borderColor: Color = colors.contentColor
) {
    OutlinedButton(
        colors = colors,
        modifier = modifier.height(54.dp),
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        enabled = enabled,
        border = BorderStroke(1.dp, color = borderColor)
    ) {
        Text(text = text, style = MaterialTheme.starterTypography.body14SemiBold)
    }
}
