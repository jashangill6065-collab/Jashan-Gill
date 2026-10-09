package com.example.model

enum class VisualThemeMode(val displayName: String, val description: String) {
  MIDNIGHT_NOIR("Midnight Noir (Default)", "Moody deep purples, crimson accents & forensic cyan"),
  CLASSIC_FILM_NOIR("Classic B&W Film Noir", "Monochromatic 1940s detective aesthetic"),
  VINTAGE_SEPIA("Vintage Estate Sepia", "Warm aged parchment, antique wood & amber lanterns"),
  CRIME_OLED("High-Contrast OLED", "Pitch true-black with stark hazard yellow & red")
}

enum class MotionSmoothness(val displayName: String) {
  CINEMATIC_SPRING("Cinematic (Fluid Springs & 60fps)"),
  BALANCED("Balanced Standard"),
  REDUCED_MOTION("Reduced Motion (Instant)")
}

data class GraphicsSettings(
  val themeMode: VisualThemeMode = VisualThemeMode.MIDNIGHT_NOIR,
  val ambientBrightness: Float = 1.0f, // 0.6f to 1.3f
  val cinematicVignetteEnabled: Boolean = true,
  val filmGrainTextureEnabled: Boolean = true,
  val motionSmoothness: MotionSmoothness = MotionSmoothness.CINEMATIC_SPRING,
  val blackoutFlashIntensity: Float = 1.0f, // 0.0f (no strobe) to 1.0f (full flash)
  val uiTextScaleMultiplier: Float = 1.0f // 0.9f to 1.25f
)
