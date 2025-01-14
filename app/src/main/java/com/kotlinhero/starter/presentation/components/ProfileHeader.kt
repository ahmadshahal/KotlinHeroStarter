package com.kotlinhero.starter.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kotlinhero.starter.core.foundation.presentation.theme.starterColors
import com.kotlinhero.starter.core.foundation.presentation.theme.starterTypography
import com.kotlinhero.starter.res.R

@Composable
fun ProfileHeader(fullName: String, email: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.ic_default_account),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(Modifier.weight(1F)) {
            Text(text = fullName, style = MaterialTheme.starterTypography.body14SemiBold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = email,
                style = MaterialTheme.starterTypography.body12Regular,
                color = MaterialTheme.starterColors.neutrals500
            )
        }
    }
}
