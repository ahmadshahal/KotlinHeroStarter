package com.kotlinhero.starter.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

data class StarterTypography(
    // Headings
    val displayOne: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 44.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (44.sp * 1.2).value.sp // 52.8.sp
    ),

    val displayTwo: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (40.sp * 1.2).value.sp // 48.sp
    ),

    val displayThree: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (32.sp * 1.2).value.sp // 38.4.sp
    ),

    val headingOne: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (28.sp * 1.2).value.sp // 33.6.sp
    ),

    val headingTwo: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (24.sp * 1.2).value.sp // 28.8.sp
    ),

    val headingThree: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (20.sp * 1.2).value.sp // 24.sp
    ),

    val headingFour: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (18.sp * 1.2).value.sp // 21.6.sp
    ),

    // Body
    val bodyBold: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (18.sp * 1.2).value.sp // 21.6.sp
    ),

    val bodySemiBold: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = (18.sp * 1.2).value.sp // 21.6.sp
    ),

    val bodyMedium: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = (18.sp * 1.2).value.sp // 21.6.sp
    ),

    val bodyRegular: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = (18.sp * 1.5).value.sp // 27.sp
    ),

    val bodySmallBold: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (16.sp * 1.2).value.sp // 19.2.sp
    ),

    val bodySmallSemiBold: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = (16.sp * 1.2).value.sp // 19.2.sp
    ),

    val bodySmallMedium: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = (16.sp * 1.2).value.sp // 19.2.sp
    ),

    val bodySmallRegular: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = (16.sp * 1.5).value.sp // 24.sp
    ),

    val body14Bold: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (14.sp * 1.2).value.sp // 16.8.sp
    ),

    val body14SemiBold: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = (14.sp * 1.2).value.sp // 16.8.sp
    ),

    val body16SemiBold: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = (16.sp * 1.2).value.sp // 16.8.sp
    ),

    val body18SemiBold: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = (18.sp * 1.2).value.sp // 16.8.sp
    ),

    val body18Medium: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = (18.sp * 1.2).value.sp // 16.8.sp
    ),

    val body14Medium: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = (14.sp * 1.2).value.sp // 16.8.sp
    ),

    val body14Regular: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = (14.sp * 1.5).value.sp // 21.sp
    ),

    val body16Regular: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = (16.sp * 1.5).value.sp
    ),

    val body12SemiBold: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = (12.sp * 1.2).value.sp // 14.4.sp
    ),

    val body12Medium: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = (12.sp * 1.2).value.sp // 14.4.sp
    ),

    val body12Regular: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = (12.sp * 1.5).value.sp // 18.sp
    ),

    val body10SemiBold: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = (10.sp * 1.2).value.sp // 12.sp
    ),

    val body10Medium: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = (10.sp * 1.2).value.sp // 12.sp
    ),

    val body10Regular: TextStyle = TextStyle(

        fontFamily = FontFamily.Default,
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = (10.sp * 1.5).value.sp // 15.sp
    )

)

private val LocalStarterTypography = staticCompositionLocalOf { StarterTypography() }

val MaterialTheme.starterTypography: StarterTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalStarterTypography.current
