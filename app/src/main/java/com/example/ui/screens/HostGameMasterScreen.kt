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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SurroundSound
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VolumeUp
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GamePhase
import com.example.ui.CrimeSceneUiState
import com.example.ui.components.CrimeTapeHeader
import com.example.ui.components.EvidenceBadge
import com.example.ui.components.SectionHeader
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
fun HostGameMasterScreen(
  uiState: CrimeSceneUiState,
  onAdvancePhase: (GamePhase) -> Unit,
  onToggleTimer: () -> Unit,
  onTriggerBlackout: () -> Unit,
  onPlaySound: (String) -> Unit,
  onUpdateGuest: (characterId: String, guestName: String, passcode: String) -> Unit,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CrimeBackground)
  ) {
    CrimeTapeHeader(text = "GAME MASTER CONSOLE // HOST PRIVILEGES ONLY")

    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onBack, modifier = Modifier.testTag("host_back_button")) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CrimeTextPrimary)
      }
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "Host Game Master Console",
          color = CrimeTextPrimary,
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "Party Timeline, Audio Cues & Staging",
          color = CrimeAccentYellow,
          fontSize = 11.sp
        )
      }
      EvidenceBadge(label = "MASTER MODE", color = CrimeAccentYellow)
    }

    HorizontalDivider(color = CrimeCardBorder, thickness = 1.dp)

    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(16.dp)
    ) {

      // TIMELINE CONTROLLER & TIMER
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CrimeAccentYellow.copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "PARTY PROGRESSION TIMER",
                color = CrimeAccentYellow,
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

            val minutes = uiState.phaseSecondsRemaining / 60
            val seconds = uiState.phaseSecondsRemaining % 60
            Text(
              text = String.format("%02d:%02d", minutes, seconds),
              color = if (uiState.isTimerRunning) CrimeAccentYellow else CrimeTextSecondary,
              fontSize = 24.sp,
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Black
            )
          }

          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = "Host Script: ${uiState.currentPhase.hostInstruction}",
            color = CrimeTextSecondary,
            fontSize = 12.sp,
            lineHeight = 16.sp
          )

          Spacer(modifier = Modifier.height(14.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Button(
              onClick = onToggleTimer,
              colors = ButtonDefaults.buttonColors(
                containerColor = if (uiState.isTimerRunning) CrimeBloodDark else CrimeAccentYellow
              ),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier
                .weight(1f)
                .testTag("toggle_timer_button")
            ) {
              Icon(
                imageVector = if (uiState.isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = null,
                tint = if (uiState.isTimerRunning) CrimeRedBright else Color.Black,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (uiState.isTimerRunning) "Pause Timer" else "Start Timer",
                color = if (uiState.isTimerRunning) CrimeRedBright else Color.Black,
                fontWeight = FontWeight.Bold
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "Switch Party Act:",
            color = CrimeTextSecondary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(6.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            GamePhase.values().forEach { phase ->
              val isCurrent = uiState.currentPhase == phase
              Box(
                modifier = Modifier
                  .weight(1f)
                  .clip(RoundedCornerShape(6.dp))
                  .background(if (isCurrent) CrimeRed else CrimeDarkSurface)
                  .border(1.dp, if (isCurrent) CrimeRedBright else CrimeCardBorder, RoundedCornerShape(6.dp))
                  .clickable { onAdvancePhase(phase) }
                  .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
              ) {
                Text(
                  text = "Act ${phase.phaseNumber}",
                  color = if (isCurrent) Color.White else CrimeTextSecondary,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // DRAMATIC SFX SOUNDBOARD & BLACKOUT TRIGGER
      SectionHeader(
        title = "Dramatic Cues & Soundboard",
        subtitle = "Trigger synchronized sound and lighting cues",
        icon = Icons.Default.SurroundSound
      )

      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CrimeRed.copy(alpha = 0.5f))
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          // Blackout Big Red Button
          Button(
            onClick = onTriggerBlackout,
            colors = ButtonDefaults.buttonColors(containerColor = CrimeRed),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(52.dp)
              .testTag("trigger_blackout_button")
          ) {
            Icon(Icons.Default.FlashOn, contentDescription = null, tint = CrimeAccentYellow, modifier = Modifier.size(22.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "TRIGGER BLACKOUT & SCREAM CUE",
              color = Color.White,
              fontWeight = FontWeight.Black,
              letterSpacing = 0.5.sp
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Soundboard Buttons
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            OutlinedButton(
              onClick = { onPlaySound("thud") },
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text("Dull Thud", fontSize = 11.sp, color = CrimeTextPrimary)
            }
            OutlinedButton(
              onClick = { onPlaySound("clock") },
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text("Clock Tick", fontSize = 11.sp, color = CrimeTextPrimary)
            }
          }

          Spacer(modifier = Modifier.height(6.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            OutlinedButton(
              onClick = { onPlaySound("chime") },
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text("Clue Sting", fontSize = 11.sp, color = CrimeForensicCyan)
            }
            OutlinedButton(
              onClick = { onPlaySound("fanfare") },
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text("Reveal Fanfare", fontSize = 11.sp, color = CrimeAccentYellow)
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // PHYSICAL HOME / BACKYARD STAGING BLUEPRINT
      SectionHeader(
        title = "Home & Backyard Staging Guide",
        subtitle = "Blueprint for turning your space into a real crime scene",
        icon = Icons.Default.Home
      )

      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CrimeCardBorder)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          StagingStep(
            stepNumber = "1",
            title = "Establish The Crime Scene Zone",
            description = "Choose your backyard patio, gazebo, deck, or a corner of the living room. Lay down a blanket or chalk mark with a wine glass on its side labeled 'EXHIBIT 1'."
          )
          Spacer(modifier = Modifier.height(10.dp))
          StagingStep(
            stepNumber = "2",
            title = "Place The Physical Clue Cards",
            description = "Print or write index cards with codes: 'CLUE-02' tucked behind the drinks bar, 'CLUE-03' in garden bushes, 'CLUE-08' under a toolbox in the garage/shed."
          )
          Spacer(modifier = Modifier.height(10.dp))
          StagingStep(
            stepNumber = "3",
            title = "Lighting & Atmosphere",
            description = "Dim overhead lights and use table lamps or outdoor string lanterns. When Act II triggers, flip the main breaker or turn off lights for 3 minutes for total immersion!"
          )
          Spacer(modifier = Modifier.height(10.dp))
          StagingStep(
            stepNumber = "4",
            title = "Secret Character Envelopes",
            description = "Assign each guest their character role before arrival or pass this phone in 'Dossier Mode' with their unique 4-digit secret passcode."
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // GUEST ASSIGNMENTS MANAGER
      SectionHeader(
        title = "Guest Role Assignments",
        subtitle = "Assign real player names & PINs to characters",
        icon = Icons.Default.AssignmentInd
      )

      uiState.characters.forEach { character ->
        GuestAssignmentRow(
          character = character,
          onSave = { name, pin -> onUpdateGuest(character.id, name, pin) }
        )
        Spacer(modifier = Modifier.height(8.dp))
      }

      Spacer(modifier = Modifier.height(28.dp))
    }
  }
}

@Composable
fun StagingStep(
  stepNumber: String,
  title: String,
  description: String
) {
  Row(modifier = Modifier.fillMaxWidth()) {
    Box(
      modifier = Modifier
        .size(24.dp)
        .clip(CircleShape)
        .background(CrimeAccentYellow.copy(alpha = 0.2f))
        .border(1.dp, CrimeAccentYellow, CircleShape),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = stepNumber,
        color = CrimeAccentYellow,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold
      )
    }
    Spacer(modifier = Modifier.width(10.dp))
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = title,
        color = CrimeTextPrimary,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = description,
        color = CrimeTextSecondary,
        fontSize = 12.sp,
        lineHeight = 16.sp
      )
    }
  }
}

@Composable
fun GuestAssignmentRow(
  character: com.example.model.SuspectCharacter,
  onSave: (String, String) -> Unit
) {
  var isEditing by remember { mutableStateOf(false) }
  var nameInput by remember { mutableStateOf(character.assignedGuestName) }
  var pinInput by remember { mutableStateOf(character.guestPasscode) }

  Card(
    modifier = Modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = CrimeDarkSurface),
    shape = RoundedCornerShape(10.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, CrimeCardBorder)
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = character.name,
            color = CrimeTextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
          )
          Text(
            text = "Role: ${character.roleTitle}",
            color = CrimeAccentYellow,
            fontSize = 11.sp
          )
        }

        OutlinedButton(
          onClick = {
            if (isEditing) {
              onSave(nameInput, pinInput)
            }
            isEditing = !isEditing
          },
          shape = RoundedCornerShape(6.dp)
        ) {
          Text(if (isEditing) "Save" else "Edit", fontSize = 11.sp)
        }
      }

      if (isEditing) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedTextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Friend's Name") },
            singleLine = true,
            modifier = Modifier.weight(2f)
          )
          OutlinedTextField(
            value = pinInput,
            onValueChange = { pinInput = it },
            label = { Text("PIN") },
            singleLine = true,
            modifier = Modifier.weight(1f)
          )
        }
      } else {
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "Assigned to: ",
            color = CrimeTextSecondary,
            fontSize = 11.sp
          )
          Text(
            text = character.assignedGuestName.ifBlank { "None assigned" },
            color = CrimeForensicCyan,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.width(12.dp))
          Text(
            text = "Passcode: ${character.guestPasscode}",
            color = CrimeTextSecondary,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace
          )
        }
      }
    }
  }
}
