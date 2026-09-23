package com.spit91.maskani.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val MaskaniColorScheme = lightColorScheme(
    primary = EmeraldPrimary,
    onPrimary = EmeraldOnPrimary,
    primaryContainer = EmeraldContainer,
    onPrimaryContainer = EmeraldOnContainer,
    secondary = OchreSecondary,
    onSecondary = OchreOnSecondary,
    background = CreamBackground,
    surface = CreamSurface,
    onBackground = CleanDarkText,
    onSurface = CleanDarkText,
    error = ErrorRed
)

@Composable
fun MaskaniTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Defaults to system setting
    content: @Composable () -> Unit
) {
    // For now, we apply our signature Maskani color scheme scheme
    MaterialTheme(
        colorScheme = MaskaniColorScheme,
        typography = MaskaniTypography,
        content = content
    )
}