package com.kotlinhero.starter.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

@Immutable
data class StarterColors(
    val lightGray: Color = Color.LightGray,
    val grey: Color = Color(0xFF707B81),
    val secondaryTextColor: Color = Color(0xFF7D848D),
    val darkText: Color = Color(0xFF1B1E28),
    val transparent: Color = Color.Transparent,
    val background: Color = Color(0xFFF8F8F8),
    val red: Color = Color(0xFFBA3232),
    val error: Color = Color(0xFFF16262),
    val primary: Color = Color(0xFF40B8E2),
    val secondary: Color = Color(0xFFFF8300),
    val primary300: Color = Color(0xFFA0DBF1),
    val greyIndicator: Color = Color(0xFFE0EAF9),
    val white: Color = Color(0xFFFFFFFF),
    val paleYellow: Color = Color(0xFFFCFF62),
    val bottomSheetBackground: Color = Color(0xFFFFFFFF),
    val baseBlack: Color = Color(0xFF111111),
    val baseWhite: Color = Color(0xffffffff),
    val neutrals100: Color = Color(0xffe3e3e3),
    val neutrals200: Color = Color(0xffcccbcb),
    val neutrals300: Color = Color(0xffb5b3b3),
    val neutrals400: Color = Color(0xff9f9c9c),
    val neutrals500: Color = Color(0xff898384),
    val neutrals600: Color = Color(0xff726c6c),
    val neutrals700: Color = Color(0xff5a5555),
    val neutrals800: Color = Color(0xff433e3f),
    val neutrals900: Color = Color(0xff2b2829),
    val neutrals1000: Color = Color(0xff151314),
    val textGrey: Color = Color(0xFF8F8F8F),
    val success: Color = Color(0xff00b31e),
    val warning: Color = Color(0xfffdb02b),
    val primary100: Color = Color(0xffdff3fa),
    val primary200: Color = Color(0xffbfe7f5),
    val primary400: Color = Color(0xff80d0ec),
    val primary500: Color = Color(0xff60c4e7),
    val primary600: Color = Color(0xff21abdb),
    val primary700: Color = Color(0xff1c91ba),
    val primary800: Color = Color(0xff177899),
    val primary900: Color = Color(0xff125e79),
    val primary1000: Color = Color(0xff0d4458),
    val secondary100: Color = Color(0xfffff3e5),
    val secondary200: Color = Color(0xffffe6cc),
    val secondary300: Color = Color(0xffffcd99),
    val secondary400: Color = Color(0xffffb566),
    val secondary500: Color = Color(0xffff9c33),
    val secondary600: Color = Color(0xffcc6900),
    val secondary700: Color = Color(0xff994f00),
    val secondary800: Color = Color(0xff663400),
    val secondary900: Color = Color(0xff4c2700),
    val secondary1000: Color = Color(0xff331a00),
    val success100: Color = Color(0xFF00B31E),
    val success200: Color = Color(0xff22ff47),
    val success300: Color = Color(0xff008015),
    val warning100: Color = Color(0x1afdb02b),
    val warning200: Color = Color(0xfffeca72),
    val warning300: Color = Color(0xffcc8202),
    val error100: Color = Color(0x1af16262),
    val error200: Color = Color(0xfff48181),
    val error300: Color = Color(0xffc94f4f),
    val purple: Color = Color(0xffDF3FAF)
)

private val LocalStarterColors = staticCompositionLocalOf { StarterColors() }

val MaterialTheme.starterColors: StarterColors
    @Composable
    @ReadOnlyComposable
    get() = LocalStarterColors.current
