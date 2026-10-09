package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.DisplaySettings
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.CrimeSceneUiState
import com.example.ui.ScreenTab
import com.example.ui.components.CrimeTapeHeader
import com.example.ui.components.EvidenceBadge
import com.example.ui.components.bouncyClickable
import com.example.ui.theme.CrimeAccentYellow
import com.example.ui.theme.CrimeBackground
import com.example.ui.theme.CrimeCardBg
import com.example.ui.theme.CrimeCardBorder
import com.example.ui.theme.CrimeDarkSurface
import com.example.ui.theme.CrimeForensicCyan
import com.example.ui.theme.CrimeRed
import com.example.ui.theme.CrimeRedBright
import com.example.ui.theme.CrimeTextPrimary
import com.example.ui.theme.CrimeTextSecondary

@Composable
fun HomeScreen(
  uiState: CrimeSceneUiState,
  onNavigate: (ScreenTab) -> Unit,
  onAdvancePhase: () -> Unit,
  onOpenGraphicsSettings: () -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CrimeBackground)
      .verticalScroll(scrollState)
  ) {
    CrimeTapeHeader()

    // Hero Banner
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(210.dp)
    ) {
      Image(
        painter = painterResource(id = R.drawable.crime_scene_manor_banner),
        contentDescription = "Crime Scene Manor Banner",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
      )

      // Dark gradient overlay
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(
            brush = Brush.verticalGradient(
              colors = listOf(
                Color.Transparent,
                CrimeBackground.copy(alpha = 0.75f),
                CrimeBackground
              )
            )
          )
      )

      // Graphics Settings Quick Icon Button in Top Right
      Box(
        modifier = Modifier
          .align(Alignment.TopEnd)
          .padding(12.dp)
      ) {
        IconButton(
          onClick = onOpenGraphicsSettings,
          modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Black.copy(alpha = 0.65f))
            .border(1.dp, CrimeAccentYellow.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .testTag("graphics_settings_top_button")
        ) {
          Icon(
            imageVector = Icons.Default.DisplaySettings,
            contentDescription = "Graphics Adjust",
            tint = CrimeAccentYellow,
            modifier = Modifier.size(20.dp)
          )
        }
      }

      Column(
        modifier = Modifier
          .align(Alignment.BottomStart)
          .padding(16.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          EvidenceBadge(label = "INTERACTIVE HOME MYSTERY", color = CrimeAccentYellow)
          Spacer(modifier = Modifier.width(8.dp))
          EvidenceBadge(label = "3-4 HOURS", color = CrimeForensicCyan)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = "The Poisoned Arbor",
          color = Color.White,
          fontSize = 24.sp,
          fontWeight = FontWeight.Black,
          letterSpacing = 0.5.sp
        )
        Text(
          text = "The Will & Murder of Lord Alistair Vance",
          color = CrimeAccentYellow,
          fontSize = 13.sp,
          fontWeight = FontWeight.Medium
        )
      }
    }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
      // Current Party Phase Banner Card
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("phase_status_card"),
        colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CrimeRed.copy(alpha = 0.45f))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "CURRENT PARTY PHASE",
                color = CrimeRedBright,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
              )
              Text(
                text = uiState.currentPhase.title,
                color = CrimeTextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
              )
            }

            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(CrimeDarkSurface)
                .border(1.dp, CrimeCardBorder, RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
              val minutes = uiState.phaseSecondsRemaining / 60
              val seconds = uiState.phaseSecondsRemaining % 60
              Text(
                text = String.format("%02d:%02d", minutes, seconds),
                color = CrimeAccentYellow,
                fontSize = 15.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = uiState.currentPhase.subtitle,
            color = CrimeTextSecondary,
            fontSize = 13.sp
          )

          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "Host Tip: ${uiState.currentPhase.hostInstruction}",
            color = CrimeAccentYellow.copy(alpha = 0.9f),
            fontSize = 12.sp,
            lineHeight = 16.sp
          )

          Spacer(modifier = Modifier.height(12.dp))
          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
              onClick = { onNavigate(ScreenTab.HOST_CONSOLE) },
              colors = ButtonDefaults.buttonColors(containerColor = CrimeRed),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier
                .weight(1f)
                .testTag("open_host_console_button")
            ) {
              Icon(Icons.Default.Tune, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Game Master", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
              onClick = onOpenGraphicsSettings,
              colors = ButtonDefaults.outlinedButtonColors(contentColor = CrimeAccentYellow),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier
                .weight(1f)
                .testTag("open_graphics_button")
            ) {
              Icon(Icons.Default.DisplaySettings, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Graphics Adjust", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Game Progress Stats Row
      val discoveredCluesCount = uiState.clues.count { it.isDiscovered }
      val totalCluesCount = uiState.clues.size
      val totalGuestsCount = uiState.characters.size

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        StatCard(
          title = "CLUES FOUND",
          value = "$discoveredCluesCount / $totalCluesCount",
          subtitle = "Hidden around home",
          icon = Icons.Default.Search,
          color = CrimeForensicCyan,
          modifier = Modifier.weight(1f)
        )
        StatCard(
          title = "GUEST ROLES",
          value = "$totalGuestsCount Roles",
          subtitle = "Secret objectives",
          icon = Icons.Default.Assignment,
          color = CrimeAccentYellow,
          modifier = Modifier.weight(1f)
        )
      }

      Spacer(modifier = Modifier.height(20.dp))

      Text(
        text = "PLAYABLE INVESTIGATION MODULES",
        color = CrimeTextSecondary,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(bottom = 8.dp)
      )

      // Module Cards
      ModuleActionCard(
        title = "Interrogation Hot Seat",
        subtitle = "Confront suspects with questions, expose nervous body tells & uncover hints",
        badge = "PLAYABLE INTERROGATION",
        icon = Icons.Default.Psychology,
        accentColor = CrimeRedBright,
        onClick = { onNavigate(ScreenTab.INTERROGATION) }
      )

      Spacer(modifier = Modifier.height(10.dp))

      ModuleActionCard(
        title = "Secret Guest Dossiers",
        subtitle = "Secret character roles, hidden motives & private objectives with PIN shield",
        badge = "${uiState.characters.size} CHARACTERS",
        icon = Icons.Default.Fingerprint,
        accentColor = CrimeAccentYellow,
        onClick = { onNavigate(ScreenTab.DOSSIERS) }
      )

      Spacer(modifier = Modifier.height(10.dp))

      ModuleActionCard(
        title = "Crime Scene & UV Lab",
        subtitle = "Search physical zones, enter clue codes, & scan with 365nm UV blacklight",
        badge = "$discoveredCluesCount DISCOVERED",
        icon = Icons.Default.Search,
        accentColor = CrimeForensicCyan,
        onClick = { onNavigate(ScreenTab.CRIME_SCENE) }
      )

      Spacer(modifier = Modifier.height(10.dp))

      ModuleActionCard(
        title = "Interactive Murder Board",
        subtitle = "Track deductions, rule out suspects (❌), set Prime Suspect & take notes",
        badge = "PLAYABLE BOARD",
        icon = Icons.Default.Visibility,
        accentColor = Color(0xFFFF8A80),
        onClick = { onNavigate(ScreenTab.MURDER_BOARD) }
      )

      Spacer(modifier = Modifier.height(10.dp))

      ModuleActionCard(
        title = "The Murder Mystery Plot",
        subtitle = "Victim, Cause of Death, Murderer & Motive, Alibis, and the 3 Major Twists",
        badge = "PLOT & TWISTS",
        icon = Icons.Default.MenuBook,
        accentColor = Color(0xFFBA68C8),
        onClick = { onNavigate(ScreenTab.PLOT_DETAILS) }
      )

      Spacer(modifier = Modifier.height(10.dp))

      ModuleActionCard(
        title = "Final Accusation & Grand Reveal",
        subtitle = "Submit suspect, weapon & motive accusations. Reveal the killer's confession!",
        badge = "FINALE",
        icon = Icons.Default.Gavel,
        accentColor = CrimeAccentYellow,
        onClick = { onNavigate(ScreenTab.VERDICT) }
      )

      Spacer(modifier = Modifier.height(28.dp))
    }
  }
}

@Composable
fun StatCard(
  title: String,
  value: String,
  subtitle: String,
  icon: ImageVector,
  color: Color,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier,
    colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
    shape = RoundedCornerShape(12.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, CrimeCardBorder)
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = title,
          color = CrimeTextSecondary,
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp
        )
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = color,
          modifier = Modifier.size(16.dp)
        )
      }
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = value,
        color = CrimeTextPrimary,
        fontSize = 18.sp,
        fontWeight = FontWeight.Black
      )
      Text(
        text = subtitle,
        color = CrimeTextSecondary,
        fontSize = 11.sp
      )
    }
  }
}

@Composable
fun ModuleActionCard(
  title: String,
  subtitle: String,
  badge: String,
  icon: ImageVector,
  accentColor: Color,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .bouncyClickable { onClick() }
      .testTag("module_card_${title.lowercase().replace(" ", "_")}"),
    colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
    shape = RoundedCornerShape(12.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, CrimeCardBorder)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(46.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(accentColor.copy(alpha = 0.15f))
          .border(1.dp, accentColor.copy(alpha = 0.35f), RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = accentColor,
          modifier = Modifier.size(24.dp)
        )
      }

      Spacer(modifier = Modifier.width(14.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = title,
            color = CrimeTextPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
          )
          EvidenceBadge(label = badge, color = accentColor)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = subtitle,
          color = CrimeTextSecondary,
          fontSize = 12.sp,
          lineHeight = 16.sp
        )
      }
    }
  }
}
