package com.kotlinhero.starter.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kotlinhero.starter.core.domain.enums.Language
import com.kotlinhero.starter.core.utils.localization.LocalizationUtils
import com.kotlinhero.starter.res.R


val plusJakarta = FontFamily(
    Font(R.font.plus_jakarta_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.plus_jakarta_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.plus_jakarta_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.plus_jakarta_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.plus_jakarta_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.plus_jakarta_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.plus_jakarta_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.plus_jakarta_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
    Font(R.font.plus_jakarta_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.plus_jakarta_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.plus_jakarta_extrabold, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.plus_jakarta_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.plus_jakarta_extralight, FontWeight.ExtraLight, FontStyle.Normal),
    Font(R.font.plus_jakarta_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic)
)

val ibmPlexAr = FontFamily(
    Font(R.font.ibm_plex_ar_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.ibm_plex_ar_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.ibm_plex_ar_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.ibm_plex_ar_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.ibm_plex_ar_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.ibm_plex_ar_extralight, FontWeight.ExtraLight, FontStyle.Normal)
)

data class StarterTypography(
    val fontFamily: FontFamily,
) {
    val displayOne: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 44.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (44.sp * 1.2).value.sp // 52.8.sp
    )

    val displayTwo: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (40.sp * 1.2).value.sp // 48.sp
    )

    val displayThree: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (32.sp * 1.2).value.sp // 38.4.sp
    )

    val headingOne: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (28.sp * 1.2).value.sp // 33.6.sp
    )

    val headingTwo: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (24.sp * 1.2).value.sp // 28.8.sp
    )

    val headingThree: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (20.sp * 1.2).value.sp // 24.sp
    )

    val headingFour: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (18.sp * 1.2).value.sp // 21.6.sp
    )

    val bodyBold: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (18.sp * 1.2).value.sp // 21.6.sp
    )

    val bodySemiBold: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = (18.sp * 1.2).value.sp // 21.6.sp
    )

    val bodyMedium: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = (18.sp * 1.2).value.sp // 21.6.sp
    )

    val bodyRegular: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = (18.sp * 1.5).value.sp // 27.sp
    )

    val bodySmallBold: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (16.sp * 1.2).value.sp // 19.2.sp
    )

    val bodySmallSemiBold: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = (16.sp * 1.2).value.sp // 19.2.sp
    )

    val bodySmallMedium: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = (16.sp * 1.2).value.sp // 19.2.sp
    )

    val bodySmallRegular: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = (16.sp * 1.5).value.sp // 24.sp
    )

    val body14Bold: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = (14.sp * 1.2).value.sp // 16.8.sp
    )

    val body14SemiBold: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = (14.sp * 1.2).value.sp // 16.8.sp
    )

    val body16SemiBold: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = (16.sp * 1.2).value.sp // 16.8.sp
    )

    val body18SemiBold: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = (18.sp * 1.2).value.sp // 16.8.sp
    )

    val body18Medium: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = (18.sp * 1.2).value.sp // 16.8.sp
    )

    val body14Medium: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = (14.sp * 1.2).value.sp // 16.8.sp
    )

    val body14Regular: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = (14.sp * 1.5).value.sp // 21.sp
    )

    val body16Regular: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = (16.sp * 1.5).value.sp
    )

    val body12SemiBold: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = (12.sp * 1.2).value.sp // 14.4.sp
    )

    val body12Medium: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = (12.sp * 1.2).value.sp // 14.4.sp
    )

    val body12Regular: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = (12.sp * 1.5).value.sp // 18.sp
    )

    val body10SemiBold: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 10.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = (10.sp * 1.2).value.sp // 12.sp
    )

    val body10Medium: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = (10.sp * 1.2).value.sp // 12.sp
    )

    val body10Regular: TextStyle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 10.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = (10.sp * 1.5).value.sp // 15.sp
    )
}

private val LocalStarterTypography = staticCompositionLocalOf<StarterTypography> {
    error("No StarterTypography provided")
}

@Composable
fun ProvideStarterTypography(content: @Composable () -> Unit) {
    val language = LocalizationUtils.getSelectedLanguage()
    val fontBasedOnLanguage = when (language) {
        Language.English -> plusJakarta
        Language.Arabic -> ibmPlexAr
    }
    val typography = StarterTypography(fontFamily = fontBasedOnLanguage)
    CompositionLocalProvider(LocalStarterTypography provides typography) {
        content()
    }
}

val MaterialTheme.starterTypography: StarterTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalStarterTypography.current