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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SuspectCharacter
import com.example.ui.CrimeSceneUiState
import com.example.ui.components.CrimeTapeHeader
import com.example.ui.components.EvidenceBadge
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
fun GuestDossierScreen(
  uiState: CrimeSceneUiState,
  onSelectCharacter: (String?) -> Unit,
  onUnlockDossier: (String) -> Boolean,
  onForceUnlock: () -> Unit,
  onToggleObjective: (characterId: String, objectiveId: String) -> Unit,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val selectedCharacter = uiState.characters.find { it.id == uiState.selectedCharacterId }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CrimeBackground)
  ) {
    CrimeTapeHeader(text = "TOP SECRET // CLASSIFIED CHARACTER DOSSIERS")

    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(
        onClick = {
          if (selectedCharacter != null) {
            onSelectCharacter(null)
          } else {
            onBack()
          }
        },
        modifier = Modifier.testTag("dossier_back_button")
      ) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CrimeTextPrimary)
      }

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = if (selectedCharacter != null) selectedCharacter.name else "Select Your Guest Role",
          color = CrimeTextPrimary,
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = if (selectedCharacter != null) selectedCharacter.roleTitle else "Confidential Agent Files",
          color = CrimeAccentYellow,
          fontSize = 11.sp
        )
      }

      if (selectedCharacter != null) {
        EvidenceBadge(
          label = if (uiState.isDossierUnlocked) "UNLOCKED" else "LOCKED",
          color = if (uiState.isDossierUnlocked) CrimeForensicCyan else CrimeRed
        )
      }
    }

    HorizontalDivider(color = CrimeCardBorder, thickness = 1.dp)

    if (selectedCharacter == null) {
      // Character Selection Grid / List
      CharacterSelectionList(
        characters = uiState.characters,
        onSelect = { onSelectCharacter(it.id) }
      )
    } else if (!uiState.isDossierUnlocked) {
      // Passcode & Privacy Shield Entry Screen
      DossierLockScreen(
        character = selectedCharacter,
        onUnlock = onUnlockDossier,
        onForceUnlock = onForceUnlock,
        onCancel = { onSelectCharacter(null) }
      )
    } else {
      // Full Confidential Character Dossier
      FullDossierView(
        character = selectedCharacter,
        onToggleObjective = { onToggleObjective(selectedCharacter.id, it) },
        onLockAgain = { onSelectCharacter(null) }
      )
    }
  }
}

@Composable
fun CharacterSelectionList(
  characters: List<SuspectCharacter>,
  onSelect: (SuspectCharacter) -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CrimeAccentYellow.copy(alpha = 0.4f))
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = "CONFIDENTIAL GUEST ASSIGNMENT",
            color = CrimeAccentYellow,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Each guest taps their assigned character below to access their secret motive, alibi, and party objectives. Keep your screen protected from other players!",
            color = CrimeTextSecondary,
            fontSize = 12.sp,
            lineHeight = 16.sp
          )
        }
      }
      Spacer(modifier = Modifier.height(6.dp))
    }

    items(characters) { character ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onSelect(character) }
          .testTag("character_card_${character.id}"),
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
              .clip(CircleShape)
              .background(CrimeDarkSurface)
              .border(1.dp, CrimeAccentYellow.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Person,
              contentDescription = null,
              tint = CrimeAccentYellow,
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
                text = character.name,
                color = CrimeTextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
              )
              EvidenceBadge(
                label = character.alias,
                color = CrimeForensicCyan
              )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = character.roleTitle,
              color = CrimeAccentYellow,
              fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "Played by: ${character.assignedGuestName.ifBlank { "Unassigned" }}",
                color = CrimeTextSecondary,
                fontSize = 11.sp
              )
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = "${character.initialObjectives.count { it.isCompleted }}/${character.initialObjectives.size} Goals",
                color = CrimeForensicCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun DossierLockScreen(
  character: SuspectCharacter,
  onUnlock: (String) -> Boolean,
  onForceUnlock: () -> Unit,
  onCancel: () -> Unit
) {
  var enteredPin by remember { mutableStateOf("") }
  var hasError by remember { mutableStateOf(false) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Box(
      modifier = Modifier
        .size(72.dp)
        .clip(CircleShape)
        .background(CrimeBloodDark.copy(alpha = 0.35f))
        .border(1.5.dp, CrimeRedBright, CircleShape),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = Icons.Default.Lock,
        contentDescription = null,
        tint = CrimeRedBright,
        modifier = Modifier.size(36.dp)
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = "CONFIDENTIAL FILE ACCESS",
      color = CrimeRedBright,
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      letterSpacing = 1.sp
    )
    Text(
      text = character.name,
      color = CrimeTextPrimary,
      fontSize = 22.sp,
      fontWeight = FontWeight.Black
    )
    Text(
      text = character.roleTitle,
      color = CrimeAccentYellow,
      fontSize = 13.sp
    )

    Spacer(modifier = Modifier.height(14.dp))

    Text(
      text = "Are you the guest playing ${character.name}? Enter your 4-digit passcode or tap 'Reveal My Role' below. Ensure no other guests are looking at your screen!",
      color = CrimeTextSecondary,
      fontSize = 12.sp,
      lineHeight = 17.sp,
      modifier = Modifier.padding(horizontal = 8.dp)
    )

    Spacer(modifier = Modifier.height(20.dp))

    OutlinedTextField(
      value = enteredPin,
      onValueChange = {
        enteredPin = it
        hasError = false
      },
      label = { Text("4-Digit Passcode (Default: ${character.guestPasscode})") },
      placeholder = { Text(character.guestPasscode) },
      visualTransformation = PasswordVisualTransformation(),
      singleLine = true,
      isError = hasError,
      colors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = CrimeAccentYellow,
        unfocusedBorderColor = CrimeCardBorder,
        focusedTextColor = CrimeTextPrimary,
        unfocusedTextColor = CrimeTextPrimary
      ),
      modifier = Modifier
        .fillMaxWidth(0.75f)
        .testTag("pin_input_field")
    )

    if (hasError) {
      Text(
        text = "Incorrect Passcode. Try ${character.guestPasscode} or tap Reveal.",
        color = CrimeRedBright,
        fontSize = 11.sp,
        modifier = Modifier.padding(top = 4.dp)
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
      onClick = {
        val success = onUnlock(enteredPin)
        if (!success) {
          hasError = true
        }
      },
      colors = ButtonDefaults.buttonColors(containerColor = CrimeRed),
      shape = RoundedCornerShape(8.dp),
      modifier = Modifier
        .fillMaxWidth(0.75f)
        .testTag("unlock_pin_button")
    ) {
      Icon(Icons.Default.Security, contentDescription = null, modifier = Modifier.size(16.dp))
      Spacer(modifier = Modifier.width(6.dp))
      Text("Unlock With PIN", fontWeight = FontWeight.Bold)
    }

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedButton(
      onClick = onForceUnlock,
      colors = ButtonDefaults.outlinedButtonColors(contentColor = CrimeAccentYellow),
      shape = RoundedCornerShape(8.dp),
      modifier = Modifier
        .fillMaxWidth(0.75f)
        .testTag("quick_reveal_button")
    ) {
      Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
      Spacer(modifier = Modifier.width(6.dp))
      Text("Quick Reveal (Shield Screen)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
  }
}

@Composable
fun FullDossierView(
  character: SuspectCharacter,
  onToggleObjective: (String) -> Unit,
  onLockAgain: () -> Unit
) {
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(16.dp)
  ) {
    // Character Header
    Card(
      modifier = Modifier.fillMaxWidth(),
      colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
      shape = RoundedCornerShape(12.dp),
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
              text = character.name,
              color = CrimeTextPrimary,
              fontSize = 20.sp,
              fontWeight = FontWeight.Black
            )
            Text(
              text = "${character.roleTitle} • Age ${character.age}",
              color = CrimeAccentYellow,
              fontSize = 12.sp,
              fontWeight = FontWeight.SemiBold
            )
          }
          EvidenceBadge(label = character.alias, color = CrimeForensicCyan)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = "Attire Suggestion: ${character.attireSuggestion}",
          color = CrimeTextSecondary,
          fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = "Public Reputation (What Everyone Knows):",
          color = CrimeTextSecondary,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = character.publicBio,
          color = CrimeTextPrimary,
          fontSize = 12.sp,
          lineHeight = 16.sp
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // SECRET DARK MOTIVE (CRITICAL - HIGHLIGHTED IN BLOOD RED)
    Card(
      modifier = Modifier.fillMaxWidth(),
      colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
      shape = RoundedCornerShape(12.dp),
      border = androidx.compose.foundation.BorderStroke(1.5.dp, CrimeRed)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Security,
            contentDescription = null,
            tint = CrimeRedBright,
            modifier = Modifier.size(18.dp)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "YOUR HIDDEN MOTIVE & SECRET (EYES ONLY)",
            color = CrimeRedBright,
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 0.5.sp
          )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = character.secretDarkMotive,
          color = CrimeTextPrimary,
          fontSize = 13.sp,
          lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(10.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(CrimeBloodDark.copy(alpha = 0.35f))
            .border(1.dp, CrimeRed.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
            .padding(10.dp)
        ) {
          Column {
            Text(
              text = "WHAT YOU MUST HIDE FROM DETECTIVES",
              color = CrimeAccentYellow,
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = character.whatTheyHide,
              color = CrimeTextPrimary,
              fontSize = 12.sp,
              lineHeight = 16.sp
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // ALIBI & THE HIDDEN FLAW
    Card(
      modifier = Modifier.fillMaxWidth(),
      colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
      shape = RoundedCornerShape(12.dp),
      border = androidx.compose.foundation.BorderStroke(1.dp, CrimeCardBorder)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = "YOUR OFFICIAL ALIBI (TELL THIS TO EVERYONE)",
          color = CrimeForensicCyan,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = character.alibiStatement,
          color = CrimeTextPrimary,
          fontSize = 12.sp,
          fontFamily = FontFamily.Serif,
          lineHeight = 16.sp
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = "THE FLAW IN YOUR ALIBI (IF QUESTIONED CLOSELY)",
          color = CrimeAccentYellow,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = character.alibiFlaw,
          color = CrimeTextSecondary,
          fontSize = 12.sp,
          lineHeight = 16.sp
        )
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // PRIVATE PARTY OBJECTIVES (INTERACTIVE CHECKBOXES)
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
            text = "SECRET PARTY OBJECTIVES",
            color = CrimeAccentYellow,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
          )
          val doneCount = character.initialObjectives.count { it.isCompleted }
          Text(
            text = "$doneCount/${character.initialObjectives.size} COMPLETED",
            color = CrimeForensicCyan,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        character.initialObjectives.forEach { objective ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clickable { onToggleObjective(objective.id) }
              .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Checkbox(
              checked = objective.isCompleted,
              onCheckedChange = { onToggleObjective(objective.id) },
              colors = CheckboxDefaults.colors(
                checkedColor = CrimeAccentYellow,
                checkmarkColor = CrimeBackground,
                uncheckedColor = CrimeCardBorder
              ),
              modifier = Modifier.testTag("objective_checkbox_${objective.id}")
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = objective.description,
              color = if (objective.isCompleted) CrimeAccentYellow else CrimeTextPrimary,
              fontSize = 12.sp,
              lineHeight = 16.sp,
              modifier = Modifier.weight(1f)
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    // GOSSIP / WHAT YOU KNOW ABOUT OTHERS
    Card(
      modifier = Modifier.fillMaxWidth(),
      colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
      shape = RoundedCornerShape(12.dp),
      border = androidx.compose.foundation.BorderStroke(1.dp, CrimeCardBorder)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = "RUMORS YOU CAN LEAK (OR TRADE AS BLACKMAIL)",
          color = CrimeForensicCyan,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          letterSpacing = 0.5.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        character.gossipKnown.forEach { gossip ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 3.dp),
            verticalAlignment = Alignment.Top
          ) {
            Text(text = "•", color = CrimeForensicCyan, fontSize = 14.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = gossip,
              color = CrimeTextSecondary,
              fontSize = 12.sp,
              lineHeight = 16.sp
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // Close & Re-Lock Button
    Button(
      onClick = onLockAgain,
      colors = ButtonDefaults.buttonColors(containerColor = CrimeDarkSurface),
      shape = RoundedCornerShape(8.dp),
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, CrimeCardBorder, RoundedCornerShape(8.dp))
        .testTag("lock_dossier_button")
    ) {
      Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(16.dp), tint = CrimeTextSecondary)
      Spacer(modifier = Modifier.width(8.dp))
      Text("Hide & Lock Dossier", color = CrimeTextSecondary, fontWeight = FontWeight.Bold)
    }

    Spacer(modifier = Modifier.height(28.dp))
  }
}
