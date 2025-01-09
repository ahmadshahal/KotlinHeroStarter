package com.kotlinhero.starter.core.foundation.presentation.reusables.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kotlinhero.starter.ui.theme.StarterTheme
import com.kotlinhero.starter.ui.theme.starterColors
import com.kotlinhero.starter.ui.theme.starterTypography

@Composable
fun NormalButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.starterColors.primary,
        contentColor = MaterialTheme.starterColors.baseWhite
    )
) {
    Button(
        colors = colors,
        modifier = modifier.height(54.dp),
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        enabled = enabled
    ) {
        Text(
            text = text,
            style = MaterialTheme.starterTypography.body14SemiBold
        )
    }
}

@Preview
@Composable
private fun StarterNormalButtonPreview() {
    StarterTheme {
        NormalButton(
            text = "Accept",
            onClick = { }
        )
    }
}
