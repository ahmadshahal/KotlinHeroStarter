package com.kotlinhero.starter.core.presentation.reusables.textfields

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kotlinhero.starter.core.presentation.theme.StarterTheme
import com.kotlinhero.starter.core.presentation.theme.starterColors
import com.kotlinhero.starter.core.presentation.theme.starterTypography
import com.kotlinhero.starter.res.R

@Composable
fun NormalTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    errorMessage: String? = null,
    prefix: (@Composable (Boolean) -> Unit)? = null,
    suffix: (@Composable (Boolean) -> Unit)? = null,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        BasicTextField(
            modifier = Modifier
                .height(55.dp)
                .fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            keyboardOptions = keyboardOptions,
            singleLine = true,
            interactionSource = interactionSource,
            keyboardActions = keyboardActions,
            textStyle = MaterialTheme.starterTypography.body14Regular.copy(
                color = MaterialTheme.starterColors.baseBlack
            ),
            visualTransformation = visualTransformation,
            cursorBrush = SolidColor(value = MaterialTheme.starterColors.primary),
        ) {
            val isFocused by interactionSource.collectIsFocusedAsState()
            val borderModifier = when {
                isError -> Modifier.border(
                    width = 2.dp,
                    color = MaterialTheme.starterColors.error100.copy(alpha = 0.3F),
                    shape = RoundedCornerShape(12.dp)
                )

                isFocused -> Modifier.border(
                    width = 2.dp,
                    color = MaterialTheme.starterColors.primary.copy(alpha = 0.5F),
                    shape = RoundedCornerShape(12.dp)
                )

                else -> Modifier.border(
                    width = 1.dp,
                    color = MaterialTheme.starterColors.neutrals100,
                    shape = RoundedCornerShape(12.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.starterColors.white)
                    .then(borderModifier)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    prefix?.let { prefix ->
                        prefix(isFocused)
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    Box(modifier = Modifier.weight(1F)) {
                        it()
                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                style = MaterialTheme.starterTypography.body14Regular.copy(
                                    color = MaterialTheme.starterColors.neutrals300
                                )
                            )
                        }
                    }
                    suffix?.let { suffix ->
                        Spacer(modifier = Modifier.width(10.dp))
                        suffix(isFocused)
                    }
                }
            }
        }
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = isError,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            errorMessage?.let {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = it,
                        style = MaterialTheme.starterTypography.body12Medium.copy(
                            color = MaterialTheme.starterColors.error
                        ),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun NormalTextFieldPreview() {
    StarterTheme {
        NormalTextField(
            value = "",
            onValueChange = {},
            hint = "Username",
            prefix = { isFocused ->
                val color =
                    if (isFocused) MaterialTheme.starterColors.neutrals500 else MaterialTheme.starterColors.neutrals200
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.ic_user),
                    tint = color,
                    contentDescription = null,
                )
            }
        )
    }
}

@Preview
@Composable
private fun NormalTextFieldErrorPreview() {
    StarterTheme {
        NormalTextField(
            value = "",
            onValueChange = {},
            hint = "Username",
            isError = true,
            errorMessage = "Username is required",
            prefix = { isFocused ->
                val color =
                    if (isFocused) MaterialTheme.starterColors.neutrals500 else MaterialTheme.starterColors.neutrals200
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.ic_user),
                    tint = color,
                    contentDescription = null,
                )
            }
        )
    }
}
