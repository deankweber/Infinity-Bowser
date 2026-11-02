package com.infinity.browser.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val DeepBlack = Color(0xFF0A0A0C)
val DarkCharcoal = Color(0xFF141418)
val GlassSurface = Color(0x22FFFFFF)
val GlassBorder = Color(0x33FFFFFF)
val ElectricBlue = Color(0xFF00E5FF)
val SoftSilver = Color(0xFFB0B0C0)
val PureWhite = Color(0xFFFFFFFF)

private val InfinityColorScheme = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = DeepBlack,
    secondary = SoftSilver,
    background = DeepBlack,
    surface = DarkCharcoal,
    onSurface = PureWhite,
    surfaceVariant = GlassSurface,
    outline = GlassBorder
)

@Composable
fun InfinityTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = InfinityColorScheme,
        content = content
    )
}
