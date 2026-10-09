package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.LocalPharmacy
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.VanceMysteryPlot
import com.example.ui.CrimeSceneUiState
import com.example.ui.components.CrimeTapeHeader
import com.example.ui.components.EvidenceBadge
import com.example.ui.components.SectionHeader
import com.example.ui.components.SpoilerShieldCard
import com.example.ui.theme.CrimeAccentYellow
import com.example.ui.theme.CrimeBackground
import com.example.ui.theme.CrimeBloodDark
import com.example.ui.theme.CrimeCardBg
import com.example.ui.theme.CrimeCardBorder
import com.example.ui.theme.CrimeDarkSurface
import com.example.ui.theme.CrimeForensicCyan
import com.example.ui.theme.CrimeRed
import com.example.ui.theme.CrimeRedBright
import com.example.ui.theme.CrimeTextPrimary
import com.example.ui.theme.CrimeTextSecondary

@Composable
fun PlotDetailsScreen(
  uiState: CrimeSceneUiState,
  onToggleSpoiler: () -> Unit,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CrimeBackground)
  ) {
    CrimeTapeHeader(text = "CLASSIFIED CASE FILE — CONFIDENTIAL PLOT OUTLINE")

    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(
        onClick = onBack,
        modifier = Modifier.testTag("plot_back_button")
      ) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CrimeTextPrimary)
      }
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "The Master Plot Outline",
          color = CrimeTextPrimary,
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "The Vance Manor Mystery Architecture",
          color = CrimeTextSecondary,
          fontSize = 11.sp
        )
      }
      EvidenceBadge(
        label = if (uiState.hostSpoilerUnlocked) "SPOILERS UNLOCKED" else "SPOILER SHIELD ON",
        color = if (uiState.hostSpoilerUnlocked) CrimeRedBright else CrimeAccentYellow
      )
    }

    HorizontalDivider(color = CrimeCardBorder, thickness = 1.dp)

    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(16.dp)
    ) {

      // Host Spoiler Warning Toggle Banner
      SpoilerShieldCard(
        isRevealed = uiState.hostSpoilerUnlocked,
        onToggleReveal = onToggleSpoiler,
        title = "HOST CONFIDENTIAL: MASTER SPOILER SHIELD"
      ) {
        Column {
          Text(
            text = "Game Master Quick Summary",
            color = CrimeAccentYellow,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Killer: Dr. Julian Ward\nMotive: Embezzlement of $3.2M + Faked terminal chart\nWeapon: Aconite neurotoxin in Vintage Port (Head wound was staged post-mortem)\nPrime Red Herrings: Vivienne's arsenic compact, Marcus's bootprints, Elena's forged will.",
            color = CrimeTextPrimary,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            fontFamily = FontFamily.Monospace
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // SECTION 1: THE VICTIM
      SectionHeader(
        title = "1. The Victim",
        subtitle = "Lord Alistair Vance (Age 64)",
        icon = Icons.Default.Person
      )

      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CrimeCardBorder)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = VanceMysteryPlot.victim.name,
              color = CrimeTextPrimary,
              fontSize = 18.sp,
              fontWeight = FontWeight.Black
            )
            EvidenceBadge(label = "VICTIM PROFILE", color = CrimeAccentYellow)
          }

          Text(
            text = "${VanceMysteryPlot.victim.title} • Age ${VanceMysteryPlot.victim.age}",
            color = CrimeAccentYellow,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
          )

          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = VanceMysteryPlot.victim.background,
            color = CrimeTextSecondary,
            fontSize = 13.sp,
            lineHeight = 18.sp
          )

          Spacer(modifier = Modifier.height(12.dp))
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(CrimeDarkSurface)
              .border(1.dp, CrimeCardBorder, RoundedCornerShape(8.dp))
              .padding(12.dp)
          ) {
            Column {
              Text(
                text = "FOUND LOCATION & SCENE CONDITION",
                color = CrimeForensicCyan,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = VanceMysteryPlot.victim.foundLocation,
                color = CrimeTextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = VanceMysteryPlot.victim.physicalAppearance,
                color = CrimeTextSecondary,
                fontSize = 12.sp,
                lineHeight = 16.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "Last Known Words:",
            color = CrimeRedBright,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = VanceMysteryPlot.victim.lastKnownWords,
            color = CrimeTextPrimary,
            fontSize = 12.sp,
            fontFamily = FontFamily.Serif,
            lineHeight = 17.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // SECTION 2: CAUSE OF DEATH
      SectionHeader(
        title = "2. Cause of Death & Forensics",
        subtitle = "Lethal neurotoxin disguised as head trauma",
        icon = Icons.Default.LocalPharmacy
      )

      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CrimeRed.copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "PRIMARY CAUSE OF DEATH",
            color = CrimeRedBright,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Text(
            text = VanceMysteryPlot.causeOfDeath.primaryCause,
            color = CrimeTextPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Black
          )

          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "True Lethal Instrument: ${VanceMysteryPlot.causeOfDeath.actualWeaponOrToxin}",
            color = CrimeTextSecondary,
            fontSize = 12.sp,
            lineHeight = 17.sp
          )

          Spacer(modifier = Modifier.height(12.dp))
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(CrimeBloodDark.copy(alpha = 0.35f))
              .border(1.dp, CrimeRed.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
              .padding(12.dp)
          ) {
            Column {
              Text(
                text = "STAGED SECONDARY WEAPON (THE RED HERRING)",
                color = CrimeAccentYellow,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = VanceMysteryPlot.causeOfDeath.stagedSecondaryWeapon,
                color = CrimeTextPrimary,
                fontSize = 12.sp,
                lineHeight = 16.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "Coroner's Inquest Findings:",
            color = CrimeForensicCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = VanceMysteryPlot.causeOfDeath.coronerFindings,
            color = CrimeTextSecondary,
            fontSize = 12.sp,
            lineHeight = 17.sp
          )

          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Toxicology Report:",
            color = CrimeForensicCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = VanceMysteryPlot.causeOfDeath.toxicologyReport,
            color = CrimeTextSecondary,
            fontSize = 12.sp,
            lineHeight = 17.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // SECTION 3: THE MURDERER & MOTIVE
      SectionHeader(
        title = "3. The Murderer & Motive",
        subtitle = "Identity, motive & execution details",
        icon = Icons.Default.Dangerous
      )

      if (uiState.hostSpoilerUnlocked) {
        Card(
          modifier = Modifier.fillMaxWidth(),
          colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
          shape = RoundedCornerShape(12.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, CrimeRed)
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = VanceMysteryPlot.murderer.characterName,
                color = CrimeRedBright,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black
              )
              EvidenceBadge(label = "THE CULPRIT", color = CrimeRedBright)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Full Motive:",
              color = CrimeAccentYellow,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = VanceMysteryPlot.murderer.fullMotive,
              color = CrimeTextPrimary,
              fontSize = 13.sp,
              lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(
              text = "Premeditation & Execution:",
              color = CrimeForensicCyan,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = VanceMysteryPlot.murderer.premeditationDetails,
              color = CrimeTextSecondary,
              fontSize = 12.sp,
              lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(
              text = "Fatal Mistake:",
              color = CrimeAccentYellow,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = VanceMysteryPlot.murderer.fatalMistake,
              color = CrimeTextSecondary,
              fontSize = 12.sp,
              lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(12.dp))
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(CrimeDarkSurface)
                .border(1.dp, CrimeCardBorder, RoundedCornerShape(8.dp))
                .padding(12.dp)
            ) {
              Column {
                Text(
                  text = "CULPRIT'S FULL CONFESSION",
                  color = CrimeRedBright,
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = VanceMysteryPlot.murderer.confessionStatement,
                  color = CrimeTextPrimary,
                  fontSize = 12.sp,
                  fontFamily = FontFamily.Serif,
                  lineHeight = 17.sp
                )
              }
            }
          }
        }
      } else {
        SpoilerShieldCard(
          isRevealed = false,
          onToggleReveal = onToggleSpoiler,
          title = "MURDERER IDENTITY & CONFESSION (HIDDEN)"
        ) {}
      }

      Spacer(modifier = Modifier.height(24.dp))

      // SECTION 4: KEY EVENTS TIMELINE
      SectionHeader(
        title = "4. Key Events Leading to Murder",
        subtitle = "Master Chronology of the Evening",
        icon = Icons.Default.History
      )

      Column(modifier = Modifier.fillMaxWidth()) {
        VanceMysteryPlot.timelineEvents.forEachIndexed { index, event ->
          TimelineEventCard(
            event = event,
            isLast = index == VanceMysteryPlot.timelineEvents.size - 1
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // SECTION 5: CHARACTER ALIBIS & FLAWS
      SectionHeader(
        title = "5. Alibis for Major Characters",
        subtitle = "Official testimonies and the critical flaw in each",
        icon = Icons.Default.Security
      )

      VanceMysteryPlot.characters.forEach { character ->
        AlibiCharacterCard(character = character, hostUnlocked = uiState.hostSpoilerUnlocked)
        Spacer(modifier = Modifier.height(10.dp))
      }

      Spacer(modifier = Modifier.height(24.dp))

      // SECTION 6: MAJOR PLOT TWISTS
      SectionHeader(
        title = "6. Major Plot Twists",
        subtitle = "3 twists for players to uncover throughout the investigation",
        icon = Icons.Default.AutoAwesome
      )

      VanceMysteryPlot.plotTwists.forEach { twist ->
        PlotTwistCard(twist = twist, hostUnlocked = uiState.hostSpoilerUnlocked)
        Spacer(modifier = Modifier.height(12.dp))
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
fun TimelineEventCard(
  event: com.example.model.TimelineEvent,
  isLast: Boolean
) {
  Row(modifier = Modifier.fillMaxWidth()) {
    // Time marker pillar
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Box(
        modifier = Modifier
          .size(28.dp)
          .clip(CircleShape)
          .background(CrimeRed.copy(alpha = 0.2f))
          .border(1.5.dp, CrimeRedBright, CircleShape),
        contentAlignment = Alignment.Center
      ) {
        Box(
          modifier = Modifier
            .size(8.dp)
            .clip(CircleShape)
            .background(CrimeRedBright)
        )
      }
      if (!isLast) {
        Box(
          modifier = Modifier
            .width(2.dp)
            .height(72.dp)
            .background(CrimeCardBorder)
        )
      }
    }

    Spacer(modifier = Modifier.width(14.dp))

    Card(
      modifier = Modifier
        .weight(1f)
        .padding(bottom = if (isLast) 0.dp else 12.dp),
      colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
      shape = RoundedCornerShape(10.dp),
      border = androidx.compose.foundation.BorderStroke(1.dp, CrimeCardBorder)
    ) {
      Column(modifier = Modifier.padding(12.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = event.title,
            color = CrimeTextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = event.time,
            color = CrimeAccentYellow,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace
          )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = event.description,
          color = CrimeTextSecondary,
          fontSize = 12.sp,
          lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(6.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
          EvidenceBadge(label = event.location, color = CrimeForensicCyan)
        }
      }
    }
  }
}

@Composable
fun AlibiCharacterCard(
  character: com.example.model.SuspectCharacter,
  hostUnlocked: Boolean
) {
  var expanded by remember { mutableStateOf(false) }

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { expanded = !expanded },
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
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = character.name,
            color = CrimeTextPrimary,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = character.roleTitle,
            color = CrimeAccentYellow,
            fontSize = 11.sp
          )
        }
        EvidenceBadge(label = character.alias, color = CrimeForensicCyan)
      }

      Spacer(modifier = Modifier.height(10.dp))
      Text(
        text = "Official Alibi Statement:",
        color = CrimeTextSecondary,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold
      )
      Text(
        text = character.alibiStatement,
        color = CrimeTextPrimary,
        fontSize = 12.sp,
        fontFamily = FontFamily.Serif,
        lineHeight = 16.sp
      )

      AnimatedVisibility(visible = expanded || hostUnlocked) {
        Column(modifier = Modifier.padding(top = 10.dp)) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(CrimeBloodDark.copy(alpha = 0.25f))
              .border(1.dp, CrimeRed.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
              .padding(10.dp)
          ) {
            Column {
              Text(
                text = "CRITICAL FLAW IN ALIBI",
                color = CrimeRedBright,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = character.alibiFlaw,
                color = CrimeTextPrimary,
                fontSize = 12.sp,
                lineHeight = 16.sp
              )
            }
          }
        }
      }

      if (!expanded && !hostUnlocked) {
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = "Tap to inspect the hole in this alibi...",
          color = CrimeAccentYellow.copy(alpha = 0.8f),
          fontSize = 10.sp,
          fontWeight = FontWeight.SemiBold
        )
      }
    }
  }
}

@Composable
fun PlotTwistCard(
  twist: com.example.model.PlotTwist,
  hostUnlocked: Boolean
) {
  var showDetails by remember { mutableStateOf(false) }

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { showDetails = !showDetails },
    colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
    shape = RoundedCornerShape(12.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, CrimeAccentYellow.copy(alpha = 0.4f))
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "TWIST #${twist.number}: ${twist.title}",
          color = CrimeAccentYellow,
          fontSize = 13.sp,
          fontWeight = FontWeight.Black
        )
        EvidenceBadge(label = "PLOT TWIST", color = CrimeAccentYellow)
      }

      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = twist.summary,
        color = CrimeTextPrimary,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium
      )

      AnimatedVisibility(visible = showDetails || hostUnlocked) {
        Column(modifier = Modifier.padding(top = 10.dp)) {
          Text(
            text = "How Detectives Discover It:",
            color = CrimeForensicCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = twist.howPlayersDiscoverIt,
            color = CrimeTextSecondary,
            fontSize = 12.sp,
            lineHeight = 16.sp
          )

          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Narrative Impact on the Game:",
            color = CrimeRedBright,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = twist.narrativeImpact,
            color = CrimeTextSecondary,
            fontSize = 12.sp,
            lineHeight = 16.sp
          )
        }
      }

      if (!showDetails && !hostUnlocked) {
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = "Tap to reveal how this twist unravels...",
          color = CrimeTextSecondary,
          fontSize = 10.sp
        )
      }
    }
  }
}
