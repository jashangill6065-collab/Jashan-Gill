package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CrimeAccentYellow
import com.example.ui.theme.CrimeCardBg
import com.example.ui.theme.CrimeCardBorder
import com.example.ui.theme.CrimeDarkSurface
import com.example.ui.theme.CrimeRed
import com.example.ui.theme.CrimeTapeBlack
import com.example.ui.theme.CrimeTapeYellow
import com.example.ui.theme.CrimeTextPrimary
import com.example.ui.theme.CrimeTextSecondary

@Composable
fun Modifier.bouncyClickable(
  onClick: () -> Unit
): Modifier {
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()
  val scale by animateFloatAsState(
    targetValue = if (isPressed) 0.965f else 1f,
    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
    label = "BouncyClickScale"
  )

  return this
    .scale(scale)
    .clickable(
      interactionSource = interactionSource,
      indication = null,
      onClick = onClick
    )
}

@Composable
fun CrimeTapeHeader(
  text: String = "CRIME SCENE — DO NOT CROSS — CONFIDENTIAL",
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "TapeShimmer")
  val shimmerOffset by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 3500, easing = LinearEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "TapeShimmerOffset"
  )

  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(26.dp)
      .background(
        brush = Brush.horizontalGradient(
          colors = listOf(
            CrimeTapeYellow,
            Color(0xFFFFD54F),
            CrimeTapeYellow,
            Color(0xFFFFE082),
            CrimeTapeYellow
          ),
          startX = shimmerOffset * 300f,
          endX = (shimmerOffset + 1f) * 600f
        )
      ),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = text.uppercase(),
      color = CrimeTapeBlack,
      fontSize = 10.sp,
      fontWeight = FontWeight.Black,
      letterSpacing = 2.sp,
      fontFamily = FontFamily.Monospace
    )
  }
}

@Composable
fun EvidenceBadge(
  label: String,
  color: Color = CrimeAccentYellow,
  isPulsing: Boolean = false,
  modifier: Modifier = Modifier
) {
  val infiniteTransition = rememberInfiniteTransition(label = "BadgePulse")
  val pulseAlpha by if (isPulsing) {
    infiniteTransition.animateFloat(
      initialValue = 0.6f,
      targetValue = 1f,
      animationSpec = infiniteRepeatable(
        animation = tween(900, easing = FastOutSlowInEasing),
        repeatMode = RepeatMode.Reverse
      ),
      label = "BadgePulseAlpha"
    )
  } else {
    remember { androidx.compose.runtime.mutableFloatStateOf(1f) }
  }

  Box(
    modifier = modifier
      .clip(RoundedCornerShape(4.dp))
      .background(color.copy(alpha = 0.18f * pulseAlpha))
      .border(1.dp, color.copy(alpha = 0.6f * pulseAlpha), RoundedCornerShape(4.dp))
      .padding(horizontal = 8.dp, vertical = 3.dp)
  ) {
    Text(
      text = label.uppercase(),
      color = color,
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.sp
    )
  }
}

@Composable
fun SectionHeader(
  title: String,
  subtitle: String? = null,
  icon: ImageVector? = null,
  actionButton: (@Composable () -> Unit)? = null,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    if (icon != null) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(CrimeRed.copy(alpha = 0.15f))
          .border(1.dp, CrimeRed.copy(alpha = 0.4f), RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = CrimeRed,
          modifier = Modifier.size(20.dp)
        )
      }
      Spacer(modifier = Modifier.width(12.dp))
    }

    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = title,
        color = CrimeTextPrimary,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
      )
      if (subtitle != null) {
        Text(
          text = subtitle,
          color = CrimeTextSecondary,
          fontSize = 12.sp
        )
      }
    }

    if (actionButton != null) {
      actionButton()
    }
  }
}

@Composable
fun SpoilerShieldCard(
  isRevealed: Boolean,
  onToggleReveal: () -> Unit,
  title: String = "SPOILER WARNING: HOST / MASTER SLEUTH ONLY",
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .animateContentSize(
        animationSpec = spring(
          dampingRatio = Spring.DampingRatioLowBouncy,
          stiffness = Spring.StiffnessMediumLow
        )
      ),
    colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
    shape = RoundedCornerShape(12.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, if (isRevealed) CrimeCardBorder else CrimeRed.copy(alpha = 0.5f))
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(if (isRevealed) CrimeDarkSurface else CrimeRed.copy(alpha = 0.18f))
          .clickable { onToggleReveal() }
          .padding(horizontal = 14.dp, vertical = 10.dp)
          .testTag("spoiler_shield_toggle"),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = if (isRevealed) Icons.Default.Visibility else Icons.Default.VisibilityOff,
          contentDescription = null,
          tint = if (isRevealed) CrimeAccentYellow else CrimeRed,
          modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
          text = title,
          color = if (isRevealed) CrimeAccentYellow else CrimeRed,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp,
          modifier = Modifier.weight(1f)
        )
        Text(
          text = if (isRevealed) "HIDE" else "TAP TO REVEAL",
          color = CrimeTextSecondary,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold
        )
      }

      if (isRevealed) {
        Box(modifier = Modifier.padding(14.dp)) {
          content()
        }
      } else {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleReveal() }
            .padding(20.dp),
          contentAlignment = Alignment.Center
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Lock,
              contentDescription = null,
              tint = CrimeTextSecondary,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Content hidden to prevent spoiling the mystery during party play.",
              color = CrimeTextSecondary,
              fontSize = 12.sp
            )
          }
        }
      }
    }
  }
}
