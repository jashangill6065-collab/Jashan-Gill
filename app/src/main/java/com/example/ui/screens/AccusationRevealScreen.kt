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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import com.example.data.VanceMysteryPlot
import com.example.model.PlayerAccusation
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccusationRevealScreen(
  uiState: CrimeSceneUiState,
  onSubmitAccusation: (PlayerAccusation) -> Unit,
  onAdvanceVerdictStep: () -> Unit,
  onResetVerdict: () -> Unit,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val scrollState = rememberScrollState()

  var guestNameInput by remember { mutableStateOf("") }
  var selectedSuspectId by remember { mutableStateOf(VanceMysteryPlot.characters.first().id) }
  var weaponInput by remember { mutableStateOf("Aconite Neurotoxin in Port") }
  var motiveInput by remember { mutableStateOf("Embezzlement & Foundation Audit") }
  var reasoningInput by remember { mutableStateOf("") }

  var suspectDropdownExpanded by remember { mutableStateOf(false) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CrimeBackground)
  ) {
    CrimeTapeHeader(text = "COURT OF ACCUSATION & FINAL VERDICT")

    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(onClick = onBack, modifier = Modifier.testTag("accusation_back_button")) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CrimeTextPrimary)
      }
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "Final Accusation & Reveal",
          color = CrimeTextPrimary,
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "Lock in votes before the killer is exposed",
          color = CrimeAccentYellow,
          fontSize = 11.sp
        )
      }
      EvidenceBadge(label = "ACT V FINALE", color = CrimeRedBright)
    }

    HorizontalDivider(color = CrimeCardBorder, thickness = 1.dp)

    Column(
      modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)
        .padding(16.dp)
    ) {

      // ACCUSATION SUBMISSION CARD
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
            Text(
              text = "SUBMIT OFFICIAL ACCUSATION",
              color = CrimeAccentYellow,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 1.sp
            )
            Icon(Icons.Default.Gavel, contentDescription = null, tint = CrimeAccentYellow, modifier = Modifier.size(18.dp))
          }

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = guestNameInput,
            onValueChange = { guestNameInput = it },
            label = { Text("Your Detective / Guest Name") },
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("detective_name_input")
          )

          Spacer(modifier = Modifier.height(8.dp))

          // Suspect Dropdown
          ExposedDropdownMenuBox(
            expanded = suspectDropdownExpanded,
            onExpandedChange = { suspectDropdownExpanded = !suspectDropdownExpanded }
          ) {
            val selectedCharacter = VanceMysteryPlot.characters.find { it.id == selectedSuspectId }
            OutlinedTextField(
              value = selectedCharacter?.name ?: "Select Suspect",
              onValueChange = {},
              readOnly = true,
              label = { Text("Accused Suspect") },
              trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = suspectDropdownExpanded) },
              modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
                .testTag("accused_suspect_dropdown")
            )
            ExposedDropdownMenu(
              expanded = suspectDropdownExpanded,
              onDismissRequest = { suspectDropdownExpanded = false }
            ) {
              VanceMysteryPlot.characters.forEach { suspect ->
                DropdownMenuItem(
                  text = { Text("${suspect.name} (${suspect.alias})") },
                  onClick = {
                    selectedSuspectId = suspect.id
                    suspectDropdownExpanded = false
                  }
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = weaponInput,
            onValueChange = { weaponInput = it },
            label = { Text("Suspected Murder Weapon / Toxin") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = motiveInput,
            onValueChange = { motiveInput = it },
            label = { Text("Suspected Motive") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = reasoningInput,
            onValueChange = { reasoningInput = it },
            label = { Text("Key Clues / Your Theory (Optional)") },
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = {
              if (guestNameInput.isNotBlank()) {
                onSubmitAccusation(
                  PlayerAccusation(
                    guestName = guestNameInput.trim(),
                    accusedCharacterId = selectedSuspectId,
                    chosenWeapon = weaponInput.trim(),
                    chosenMotive = motiveInput.trim(),
                    reasoning = reasoningInput.trim()
                  )
                )
                guestNameInput = ""
                reasoningInput = ""
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = CrimeRed),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("submit_accusation_button")
          ) {
            Icon(Icons.Default.Gavel, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Lock In Accusation", fontWeight = FontWeight.Bold)
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // ACCUSATION WALL
      if (uiState.playerAccusations.isNotEmpty()) {
        Text(
          text = "LOCKED-IN ACCUSATIONS (${uiState.playerAccusations.size})",
          color = CrimeTextSecondary,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp,
          modifier = Modifier.padding(bottom = 6.dp)
        )

        uiState.playerAccusations.forEach { accusation ->
          val suspect = VanceMysteryPlot.characters.find { it.id == accusation.accusedCharacterId }
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp),
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
                  text = "Detective: ${accusation.guestName}",
                  color = CrimeAccentYellow,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold
                )
                EvidenceBadge(
                  label = suspect?.name ?: "Unknown",
                  color = if (accusation.accusedCharacterId == "dr_ward") CrimeRedBright else CrimeForensicCyan
                )
              }
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Weapon: ${accusation.chosenWeapon} • Motive: ${accusation.chosenMotive}",
                color = CrimeTextPrimary,
                fontSize = 11.sp
              )
              if (accusation.reasoning.isNotBlank()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = "\"${accusation.reasoning}\"",
                  color = CrimeTextSecondary,
                  fontSize = 11.sp,
                  fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))
      }

      // DRAMATIC MULTI-STEP GRAND REVEAL SEQUENCE
      SectionHeader(
        title = "The Grand Reveal Sequence",
        subtitle = "Walk all guests through the climactic unmasking",
        icon = Icons.Default.EmojiEvents
      )

      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CrimeDarkSurface),
        shape = RoundedCornerShape(14.dp),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, CrimeRed)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "REVELATION PROGRESSION",
              color = CrimeRedBright,
              fontSize = 11.sp,
              fontWeight = FontWeight.Black,
              letterSpacing = 1.sp
            )
            Text(
              text = "Step ${uiState.verdictStep} of 3",
              color = CrimeAccentYellow,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Step 0: Ready
          if (uiState.verdictStep == 0) {
            Text(
              text = "Gather all suspects in the parlor or around the patio. When everyone is seated and drinks are in hand, press 'Begin Reveal' below.",
              color = CrimeTextPrimary,
              fontSize = 13.sp,
              lineHeight = 18.sp
            )
          }

          // Step 1: The Red Herring Weapon & True Toxin
          if (uiState.verdictStep >= 1) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(CrimeBloodDark.copy(alpha = 0.25f))
                .border(1.dp, CrimeRed.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                .padding(10.dp)
            ) {
              Column {
                Text(
                  text = "STEP 1: THE WEAPON RED HERRING",
                  color = CrimeRedBright,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = "The bloody fire poker on the flagstones was NOT the cause of death! Forensics proved Alistair's heart had already stopped before his head hit stone. The lethal instrument was concentrated Aconite Neurotoxin in his 1982 Port decanter!",
                  color = CrimeTextPrimary,
                  fontSize = 12.sp,
                  lineHeight = 16.sp
                )
              }
            }
            Spacer(modifier = Modifier.height(8.dp))
          }

          // Step 2: The Double Poison Twist
          if (uiState.verdictStep >= 2) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(CrimeDarkSurface)
                .border(1.dp, CrimeCardBorder, RoundedCornerShape(8.dp))
                .padding(10.dp)
            ) {
              Column {
                Text(
                  text = "STEP 2: THE DOUBLE POISON ATTEMPT",
                  color = CrimeAccentYellow,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = "Vivienne Delacroix DID bring poison in her silver compact—pure arsenic! She intended to poison Alistair's evening tea, but when she approached the gazebo at 8:15 PM, someone else had already beaten her to it!",
                  color = CrimeTextPrimary,
                  fontSize = 12.sp,
                  lineHeight = 16.sp
                )
              }
            }
            Spacer(modifier = Modifier.height(8.dp))
          }

          // Step 3: Unmasking the Murderer & The Confession
          if (uiState.verdictStep >= 3) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(CrimeBloodDark)
                .border(1.5.dp, CrimeRedBright, RoundedCornerShape(8.dp))
                .padding(12.dp)
            ) {
              Column {
                Text(
                  text = "THE MURDERER: DR. JULIAN WARD!",
                  color = Color.White,
                  fontSize = 15.sp,
                  fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "Lord Vance caught Dr. Ward embezzling $3.2 Million from the Medical Trust and faking Vance's terminal heart disease. Alistair gave him until 9:00 PM to confess or go to prison.\n\nLord Vance's dying handkerchief note named his killer: 'J.W. ... Port ... Check Shed Safe'.",
                  color = CrimeAccentYellow,
                  fontSize = 12.sp,
                  lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                  text = "Dr. Ward's Confession:",
                  color = Color.White,
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
                Text(
                  text = VanceMysteryPlot.murderer.confessionStatement,
                  color = Color.White.copy(alpha = 0.9f),
                  fontSize = 12.sp,
                  fontFamily = FontFamily.Serif,
                  lineHeight = 16.sp
                )
              }
            }
            Spacer(modifier = Modifier.height(8.dp))
          }

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            if (uiState.verdictStep < 3) {
              Button(
                onClick = onAdvanceVerdictStep,
                colors = ButtonDefaults.buttonColors(containerColor = CrimeRed),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                  .weight(1f)
                  .testTag("advance_verdict_button")
              ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = if (uiState.verdictStep == 0) "Begin Reveal" else "Next Revelation",
                  fontWeight = FontWeight.Bold
                )
              }
            } else {
              OutlinedButton(
                onClick = onResetVerdict,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = CrimeTextSecondary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
              ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Reset Revelation")
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(30.dp))
    }
  }
}
