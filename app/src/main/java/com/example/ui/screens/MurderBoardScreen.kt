package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.VanceMysteryPlot
import com.example.ui.CrimeSceneUiState
import com.example.ui.components.CrimeTapeHeader
import com.example.ui.components.EvidenceBadge
import com.example.ui.components.bouncyClickable
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

data class BoardNodeConnection(
  val characterId: String,
  val suspectName: String,
  val relation: String,
  val connectedClues: List<String>,
  val motiveHypothesis: String,
  val threatColor: Color
)

@Composable
fun MurderBoardScreen(
  uiState: CrimeSceneUiState,
  onToggleEliminated: (String) -> Unit,
  onSetPrimeSuspect: (String) -> Unit,
  onUpdateNotes: (String) -> Unit,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()
  var selectedSuspectKey by remember { mutableStateOf<String?>("dr_ward") }

  val infiniteTransition = rememberInfiniteTransition(label = "PinPulse")
  val pinPulseScale by infiniteTransition.animateFloat(
    initialValue = 0.85f,
    targetValue = 1.15f,
    animationSpec = infiniteRepeatable(
      animation = tween(1200, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse
    ),
    label = "PinPulseScale"
  )

  val suspectConnections = listOf(
    BoardNodeConnection(
      characterId = "dr_ward",
      suspectName = "Dr. Julian Ward",
      relation = "Personal Physician & Old Friend",
      connectedClues = listOf("CLUE-01: Shattered Port Glass (Aconite)", "CLUE-02: Monogrammed Pen 'J.W.'", "CLUE-08: Embezzlement Audit"),
      motiveHypothesis = "Embezzled $3.2M. Faced immediate police exposure and forfeiture of medical license at 9:00 PM tonight.",
      threatColor = CrimeRedBright
    ),
    BoardNodeConnection(
      characterId = "elena_vance",
      suspectName = "Elena Vance",
      relation = "Estranged Heiress / Daughter",
      connectedClues = listOf("CLUE-04: Forged Will Draft", "Milan Call Dropped at 8:06 PM", "Conservatory Argument"),
      motiveHypothesis = "Massive $850k casino syndicate debts due tonight. Broke into study safe to replace will with forged copy.",
      threatColor = Color(0xFFFF8A80)
    ),
    BoardNodeConnection(
      characterId = "marcus_sterling",
      suspectName = "Marcus 'Fox' Sterling",
      relation = "Estate Caretaker & Botanist",
      connectedClues = listOf("CLUE-05: Muddy Size 11 Bootprint", "CLUE-09: Tripped Master Breaker", "Stolen Blackmail Ledger"),
      motiveHypothesis = "Ran black-market orchid & aconite sap ring. Vance threatened arrest. Bootprints found at birdbath.",
      threatColor = CrimeAccentYellow
    ),
    BoardNodeConnection(
      characterId = "vivienne_delacroix",
      suspectName = "Vivienne Delacroix",
      relation = "Fiancée of 4 Months",
      connectedClues = listOf("CLUE-06: Arsenic Makeup Compact", "Expiring Pre-Nuptial", "Window Sighting at 8:15 PM"),
      motiveHypothesis = "Brought arsenic in compact to poison bedtime tea. But someone else's aconite killed him first!",
      threatColor = Color(0xFFBA68C8)
    )
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CrimeBackground)
  ) {
    CrimeTapeHeader(text = "INTERACTIVE MURDER BOARD // CASE NETWORK")

    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onBack, modifier = Modifier.testTag("murder_board_back_button")) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CrimeTextPrimary)
      }
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "The Detective's Murder Board",
          color = CrimeTextPrimary,
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "Tap suspects to track deductions & eliminate",
          color = CrimeAccentYellow,
          fontSize = 11.sp
        )
      }
      EvidenceBadge(
        label = "${uiState.eliminatedSuspectIds.size} RULED OUT",
        color = if (uiState.eliminatedSuspectIds.isNotEmpty()) CrimeRed else CrimeTextSecondary
      )
    }

    HorizontalDivider(color = CrimeCardBorder, thickness = 1.dp)

    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(16.dp)
    ) {
      // Visual Corkboard Representation with Pins
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CrimeCardBorder)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.PushPin, contentDescription = null, tint = CrimeRedBright, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "CASE CENTER: LORD ALISTAIR VANCE",
                color = CrimeRedBright,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
              )
            }
            EvidenceBadge(label = "FOUND 8:30 PM", color = CrimeForensicCyan)
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Lethal Neurotoxin Ingestion (7:40-7:55 PM) • Staged Head Trauma at Gazebo Birdbath",
            color = CrimeTextSecondary,
            fontSize = 12.sp
          )

          Spacer(modifier = Modifier.height(14.dp))

          // String connection canvas illustration
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(64.dp)
              .clip(RoundedCornerShape(8.dp))
              .background(CrimeDarkSurface)
          ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
              val w = size.width
              val h = size.height
              val centerPin = Offset(w * 0.5f, 10f)

              val leftSuspect = Offset(w * 0.15f, h - 12f)
              val midLeft = Offset(w * 0.38f, h - 12f)
              val midRight = Offset(w * 0.62f, h - 12f)
              val rightSuspect = Offset(w * 0.85f, h - 12f)

              // Draw red string lines
              val stringPaint = Color(0xFFD32F2F)
              drawLine(stringPaint, centerPin, leftSuspect, strokeWidth = 3f)
              drawLine(stringPaint, centerPin, midLeft, strokeWidth = 3f)
              drawLine(stringPaint, centerPin, midRight, strokeWidth = 3f)
              drawLine(stringPaint, centerPin, rightSuspect, strokeWidth = 3f)

              // Draw animated pulsing center pin
              drawCircle(Color(0xFFFF5252), radius = 6f * pinPulseScale, center = centerPin)
              drawCircle(Color(0xFFE0B86C), radius = 4f, center = leftSuspect)
              drawCircle(Color(0xFFE0B86C), radius = 4f, center = midLeft)
              drawCircle(Color(0xFFE0B86C), radius = 4f, center = midRight)
              drawCircle(Color(0xFFE0B86C), radius = 4f, center = rightSuspect)
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "ACTIVE SUSPECT THREADS & PLAYABLE ELIMINATIONS",
        color = CrimeTextSecondary,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(bottom = 6.dp)
      )

      suspectConnections.forEach { connection ->
        val isSelected = selectedSuspectKey == connection.characterId
        val isEliminated = uiState.eliminatedSuspectIds.contains(connection.characterId)
        val isPrime = uiState.primeSuspectId == connection.characterId

        Card(
          modifier = Modifier
            .fillMaxWidth()
            .clickable {
              selectedSuspectKey = if (isSelected) null else connection.characterId
            }
            .padding(vertical = 4.dp)
            .testTag("murder_board_suspect_${connection.characterId}"),
          colors = CardDefaults.cardColors(
            containerColor = if (isEliminated) Color(0xFF131018) else CrimeCardBg
          ),
          shape = RoundedCornerShape(12.dp),
          border = androidx.compose.foundation.BorderStroke(
            if (isPrime) 2.dp else if (isSelected) 1.5.dp else 1.dp,
            if (isPrime) CrimeAccentYellow else if (isSelected) connection.threatColor else CrimeCardBorder
          )
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(if (isEliminated) Color.Gray else connection.threatColor)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = connection.suspectName,
                  color = if (isEliminated) Color.Gray else CrimeTextPrimary,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  textDecoration = if (isEliminated) TextDecoration.LineThrough else TextDecoration.None
                )
              }

              Row(verticalAlignment = Alignment.CenterVertically) {
                if (isPrime) {
                  EvidenceBadge(label = "★ PRIME SUSPECT", color = CrimeAccentYellow)
                  Spacer(modifier = Modifier.width(6.dp))
                }
                EvidenceBadge(
                  label = if (isEliminated) "RULED OUT" else connection.relation,
                  color = if (isEliminated) Color.Gray else connection.threatColor
                )
              }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
              text = connection.motiveHypothesis,
              color = if (isEliminated) Color.Gray else CrimeTextSecondary,
              fontSize = 12.sp,
              lineHeight = 16.sp
            )

            // Playable Action Buttons: Prime Suspect & Rule Out
            Spacer(modifier = Modifier.height(10.dp))
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              OutlinedButton(
                onClick = { onSetPrimeSuspect(connection.characterId) },
                colors = ButtonDefaults.outlinedButtonColors(
                  contentColor = if (isPrime) CrimeAccentYellow else CrimeTextSecondary
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                  .weight(1f)
                  .testTag("toggle_prime_${connection.characterId}")
              ) {
                Icon(
                  imageVector = if (isPrime) Icons.Default.Star else Icons.Default.StarBorder,
                  contentDescription = null,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(if (isPrime) "Prime Suspect" else "Set as Prime", fontSize = 11.sp)
              }

              OutlinedButton(
                onClick = { onToggleEliminated(connection.characterId) },
                colors = ButtonDefaults.outlinedButtonColors(
                  contentColor = if (isEliminated) CrimeRedBright else CrimeTextSecondary
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                  .weight(1f)
                  .testTag("toggle_eliminate_${connection.characterId}")
              ) {
                Icon(
                  imageVector = if (isEliminated) Icons.Default.CheckCircle else Icons.Default.Cancel,
                  contentDescription = null,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(if (isEliminated) "Restore" else "Rule Out (❌)", fontSize = 11.sp)
              }
            }

            if (isSelected) {
              Spacer(modifier = Modifier.height(12.dp))
              Text(
                text = "CONNECTED PHYSICAL EVIDENCE (RED STRINGS):",
                color = connection.threatColor,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
              )
              Spacer(modifier = Modifier.height(4.dp))
              connection.connectedClues.forEach { clue ->
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(Icons.Default.Link, contentDescription = null, tint = connection.threatColor, modifier = Modifier.size(14.dp))
                  Spacer(modifier = Modifier.width(6.dp))
                  Text(text = clue, color = CrimeTextPrimary, fontSize = 12.sp)
                }
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // DETECTIVE SCRATCHPAD / NOTEBOOK
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CrimeAccentYellow.copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.EditNote, contentDescription = null, tint = CrimeAccentYellow, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "PERSONAL DETECTIVE NOTEBOOK",
              color = CrimeAccentYellow,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.5.sp
            )
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Jot down your working theories, suspect contradictions, or whisper codes during the party:",
            color = CrimeTextSecondary,
            fontSize = 11.sp
          )

          Spacer(modifier = Modifier.height(8.dp))
          OutlinedTextField(
            value = uiState.detectiveNotes,
            onValueChange = onUpdateNotes,
            placeholder = { Text("e.g. Dr. Ward lied about the Lancet reading; check Marcus's shed key...") },
            minLines = 3,
            maxLines = 6,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = CrimeAccentYellow,
              unfocusedBorderColor = CrimeCardBorder,
              focusedTextColor = CrimeTextPrimary,
              unfocusedTextColor = CrimeTextPrimary
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("detective_notes_input")
          )
        }
      }

      Spacer(modifier = Modifier.height(28.dp))
    }
  }
}
