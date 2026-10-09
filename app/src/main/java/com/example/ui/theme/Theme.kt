package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val NoirCrimeColorScheme = darkColorScheme(
  primary = CrimeRedBright,
  onPrimary = Color.White,
  primaryContainer = CrimeBloodDark,
  onPrimaryContainer = Color(0xFFFFDAD6),
  secondary = CrimeAccentYellow,
  onSecondary = Color(0xFF211B00),
  secondaryContainer = Color(0xFF382E00),
  onSecondaryContainer = Color(0xFFFFE082),
  tertiary = CrimeForensicCyan,
  onTertiary = Color.Black,
  tertiaryContainer = Color(0xFF004D5A),
  onTertiaryContainer = Color(0xFF80DEEA),
  background = CrimeBackground,
  onBackground = CrimeTextPrimary,
  surface = CrimeDarkSurface,
  onSurface = CrimeTextPrimary,
  surfaceVariant = CrimeCardBg,
  onSurfaceVariant = CrimeTextSecondary,
  outline = CrimeCardBorder,
  error = CrimeRed,
  onError = Color.White
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true,
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  // Always use the moody, atmospheric noir crime scheme for authentic murder mystery vibe
  MaterialTheme(
    colorScheme = NoirCrimeColorScheme,
    typography = Typography,
    content = content
  )
}

