package com.jacobdgraham.myschedule.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val SoftLightGrayBackground = Color(0xFFF2F2F2)
val CalendarCellBackground = Color(0xFFF8F3FA)

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650A4)
val PurpleGrey40 = Color(0xFF625B71)
val Pink40 = Color(0xFF7D5260)

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = SoftLightGrayBackground,
    surface = SoftLightGrayBackground
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = SoftLightGrayBackground,
    surface = SoftLightGrayBackground
)
@Composable
fun MyScheduleTheme(content: @Composable () -> Unit) {
    MaterialTheme(content = content)
}