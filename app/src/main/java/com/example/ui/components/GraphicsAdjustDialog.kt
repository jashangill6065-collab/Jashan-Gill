package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DisplaySettings
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.GraphicsSettings
import com.example.model.MotionSmoothness
import com.example.model.VisualThemeMode
import com.example.ui.theme.CrimeAccentYellow
import com.example.ui.theme.CrimeBloodDark
import com.example.ui.theme.CrimeCardBg
import com.example.ui.theme.CrimeCardBorder
import com.example.ui.theme.CrimeDarkSurface
import com.example.ui.theme.CrimeForensicCyan
import com.example.ui.theme.CrimeRed
import com.example.ui.theme.CrimeRedBright
import com.example.ui.theme.CrimeTextPrimary
import com.example.ui.theme.CrimeTextSecondary
import kotlin.math.roundToInt

@Composable
fun GraphicsAdjustDialog(
  settings: GraphicsSettings,
  onUpdateSettings: (GraphicsSettings) -> Unit,
  onDismiss: () -> Unit
) {
  val scrollState = rememberScrollState()

  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)
        .testTag("graphics_adjust_dialog"),
      colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
      shape = RoundedCornerShape(16.dp),
      border = androidx.compose.foundation.BorderStroke(1.5.dp, CrimeAccentYellow.copy(alpha = 0.6f))
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
          .verticalScroll(scrollState)
      ) {
        // Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(CrimeAccentYellow.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = null,
                tint = CrimeAccentYellow,
                modifier = Modifier.size(20.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "Graphics & Ambiance",
                color = CrimeTextPrimary,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = "Visual Presets & Display Adjustments",
                color = CrimeTextSecondary,
                fontSize = 11.sp
              )
            }
          }

          IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
            Icon(Icons.Default.Close, contentDescription = "Close", tint = CrimeTextSecondary)
          }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = CrimeCardBorder)

        // 1. VISUAL THEME FILTER
        Text(
          text = "ATMOSPHERIC THEME FILTER",
          color = CrimeAccentYellow,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(6.dp))

        VisualThemeMode.values().forEach { mode ->
          val isSelected = settings.themeMode == mode
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp)
              .clickable { onUpdateSettings(settings.copy(themeMode = mode)) }
              .testTag("theme_mode_${mode.name.lowercase()}"),
            colors = CardDefaults.cardColors(
              containerColor = if (isSelected) CrimeDarkSurface else CrimeCardBg
            ),
            shape = RoundedCornerShape(8.dp),
            border = androidx.compose.foundation.BorderStroke(
              if (isSelected) 1.5.dp else 1.dp,
              if (isSelected) CrimeAccentYellow else CrimeCardBorder
            )
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text(
                text = mode.displayName,
                color = if (isSelected) CrimeAccentYellow else CrimeTextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = mode.description,
                color = CrimeTextSecondary,
                fontSize = 11.sp
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. AMBIENT BRIGHTNESS & GAMMA
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "AMBIENT BRIGHTNESS",
            color = CrimeAccentYellow,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Text(
            text = "${(settings.ambientBrightness * 100).roundToInt()}%",
            color = CrimeForensicCyan,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }
        Slider(
          value = settings.ambientBrightness,
          onValueChange = { onUpdateSettings(settings.copy(ambientBrightness = it)) },
          valueRange = 0.7f..1.3f,
          colors = SliderDefaults.colors(
            thumbColor = CrimeAccentYellow,
            activeTrackColor = CrimeAccentYellow,
            inactiveTrackColor = CrimeCardBorder
          ),
          modifier = Modifier.testTag("brightness_slider")
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 3. CINEMATIC VIGNETTE TOGGLE
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "Cinematic Vignette Shadows",
              color = CrimeTextPrimary,
              fontSize = 13.sp,
              fontWeight = FontWeight.SemiBold
            )
            Text(
              text = "Dark shadowy lens borders around screen edges",
              color = CrimeTextSecondary,
              fontSize = 11.sp
            )
          }
          Switch(
            checked = settings.cinematicVignetteEnabled,
            onCheckedChange = { onUpdateSettings(settings.copy(cinematicVignetteEnabled = it)) },
            colors = SwitchDefaults.colors(
              checkedThumbColor = Color.Black,
              checkedTrackColor = CrimeAccentYellow,
              uncheckedBorderColor = CrimeCardBorder
            ),
            modifier = Modifier.testTag("vignette_switch")
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 4. MOTION SMOOTHNESS
        Text(
          text = "ANIMATION & MOTION FLUIDITY",
          color = CrimeAccentYellow,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          MotionSmoothness.values().forEach { motion ->
            val isSelected = settings.motionSmoothness == motion
            Box(
              modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(if (isSelected) CrimeAccentYellow else CrimeDarkSurface)
                .border(1.dp, if (isSelected) CrimeAccentYellow else CrimeCardBorder, RoundedCornerShape(8.dp))
                .clickable { onUpdateSettings(settings.copy(motionSmoothness = motion)) }
                .padding(vertical = 8.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = when (motion) {
                  MotionSmoothness.CINEMATIC_SPRING -> "Cinematic"
                  MotionSmoothness.BALANCED -> "Balanced"
                  MotionSmoothness.REDUCED_MOTION -> "Reduced"
                },
                color = if (isSelected) Color.Black else CrimeTextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 5. BLACKOUT STROBE INTENSITY
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "BLACKOUT STROBE FLASH",
            color = CrimeAccentYellow,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Text(
            text = "${(settings.blackoutFlashIntensity * 100).roundToInt()}%",
            color = CrimeRedBright,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
          )
        }
        Slider(
          value = settings.blackoutFlashIntensity,
          onValueChange = { onUpdateSettings(settings.copy(blackoutFlashIntensity = it)) },
          valueRange = 0.0f..1.0f,
          colors = SliderDefaults.colors(
            thumbColor = CrimeRedBright,
            activeTrackColor = CrimeRed,
            inactiveTrackColor = CrimeCardBorder
          ),
          modifier = Modifier.testTag("blackout_flash_slider")
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Done Button
        Button(
          onClick = onDismiss,
          colors = ButtonDefaults.buttonColors(containerColor = CrimeAccentYellow),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("close_graphics_settings_button")
        ) {
          Text(
            text = "Apply & Return to Game",
            color = Color.Black,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}
