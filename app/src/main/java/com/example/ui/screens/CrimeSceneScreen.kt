package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.CrimeSceneClue
import com.example.model.EvidenceCategory
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
import kotlinx.coroutines.delay

@Composable
fun CrimeSceneScreen(
  uiState: CrimeSceneUiState,
  onClueCodeChange: (String) -> Unit,
  onSubmitClueCode: () -> Unit,
  onMarkClueDiscovered: (String) -> Unit,
  onSelectClueForForensics: (CrimeSceneClue?) -> Unit,
  onRunReagentTest: () -> Unit,
  onToggleUvLight: () -> Unit,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedCategoryFilter by remember { mutableStateOf<EvidenceCategory?>(null) }
  val filteredClues = if (selectedCategoryFilter == null) {
    uiState.clues
  } else {
    uiState.clues.filter { it.category == selectedCategoryFilter }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CrimeBackground)
  ) {
    CrimeTapeHeader(text = "EVIDENCE RECOVERY & FORENSICS LAB")

    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(
        onClick = onBack,
        modifier = Modifier.testTag("crime_scene_back_button")
      ) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CrimeTextPrimary)
      }
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "Physical Crime Scene Clues",
          color = CrimeTextPrimary,
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "${uiState.clues.count { it.isDiscovered }}/${uiState.clues.size} Items Discovered",
          color = CrimeForensicCyan,
          fontSize = 11.sp
        )
      }

      // UV Light Toggle Button
      Button(
        onClick = onToggleUvLight,
        colors = ButtonDefaults.buttonColors(
          containerColor = if (uiState.isUvLightActive) Color(0xFF7C4DFF) else CrimeDarkSurface
        ),
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(
          1.dp,
          if (uiState.isUvLightActive) Color(0xFFB388FF) else CrimeCardBorder
        ),
        modifier = Modifier.testTag("toggle_uv_light_button")
      ) {
        Icon(
          imageVector = Icons.Default.Lightbulb,
          contentDescription = null,
          tint = if (uiState.isUvLightActive) Color.White else CrimeTextSecondary,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = if (uiState.isUvLightActive) "UV ON" else "UV OFF",
          color = if (uiState.isUvLightActive) Color.White else CrimeTextSecondary,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold
        )
      }
    }

    HorizontalDivider(color = CrimeCardBorder, thickness = 1.dp)

    if (uiState.selectedClueForForensics != null) {
      // Forensics Lab View for the selected clue
      ForensicsLabView(
        clue = uiState.selectedClueForForensics,
        isTested = uiState.forensicsReagentTested,
        isUvActive = uiState.isUvLightActive,
        onRunReagentTest = onRunReagentTest,
        onToggleUv = onToggleUvLight,
        onClose = { onSelectClueForForensics(null) }
      )
    } else {
      // Clue Search & Catalog
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        item {
          Spacer(modifier = Modifier.height(6.dp))

          // Clue Code Input Box (for finding cards hidden around the house)
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, CrimeAccentYellow.copy(alpha = 0.5f))
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Text(
                text = "FOUND A PHYSICAL CLUE CARD IN THE HOUSE?",
                color = CrimeAccentYellow,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
              )
              Text(
                text = "Type the code on the card (e.g. CLUE-01, CLUE-02) to log evidence into the detective database.",
                color = CrimeTextSecondary,
                fontSize = 12.sp,
                modifier = Modifier.padding(vertical = 4.dp)
              )

              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
              ) {
                OutlinedTextField(
                  value = uiState.clueCodeInput,
                  onValueChange = onClueCodeChange,
                  placeholder = { Text("e.g. CLUE-03") },
                  singleLine = true,
                  colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CrimeAccentYellow,
                    unfocusedBorderColor = CrimeCardBorder,
                    focusedTextColor = CrimeTextPrimary,
                    unfocusedTextColor = CrimeTextPrimary
                  ),
                  modifier = Modifier
                    .weight(1f)
                    .testTag("clue_code_input")
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                  onClick = onSubmitClueCode,
                  colors = ButtonDefaults.buttonColors(containerColor = CrimeRed),
                  shape = RoundedCornerShape(8.dp),
                  modifier = Modifier.testTag("submit_clue_code_button")
                ) {
                  Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("Log", fontWeight = FontWeight.Bold)
                }
              }

              if (uiState.clueCodeErrorMessage != null) {
                Text(
                  text = uiState.clueCodeErrorMessage,
                  color = CrimeRedBright,
                  fontSize = 11.sp,
                  modifier = Modifier.padding(top = 4.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Filter by Category
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            FilterChip(
              label = "All",
              isSelected = selectedCategoryFilter == null,
              onClick = { selectedCategoryFilter = null }
            )
            FilterChip(
              label = "Weapons",
              isSelected = selectedCategoryFilter == EvidenceCategory.WEAPON,
              onClick = { selectedCategoryFilter = EvidenceCategory.WEAPON }
            )
            FilterChip(
              label = "Toxins",
              isSelected = selectedCategoryFilter == EvidenceCategory.TOXIN,
              onClick = { selectedCategoryFilter = EvidenceCategory.TOXIN }
            )
            FilterChip(
              label = "Docs",
              isSelected = selectedCategoryFilter == EvidenceCategory.DOCUMENT,
              onClick = { selectedCategoryFilter = EvidenceCategory.DOCUMENT }
            )
            FilterChip(
              label = "Traces",
              isSelected = selectedCategoryFilter == EvidenceCategory.TRACE,
              onClick = { selectedCategoryFilter = EvidenceCategory.TRACE }
            )
          }

          Spacer(modifier = Modifier.height(8.dp))
        }

        items(filteredClues) { clue ->
          ClueItemCard(
            clue = clue,
            isUvActive = uiState.isUvLightActive,
            onDiscover = { onMarkClueDiscovered(clue.id) },
            onInspectForensics = { onSelectClueForForensics(clue) }
          )
        }

        item {
          Spacer(modifier = Modifier.height(24.dp))
        }
      }
    }
  }
}

@Composable
fun FilterChip(
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(16.dp))
      .background(if (isSelected) CrimeAccentYellow else CrimeDarkSurface)
      .border(1.dp, if (isSelected) CrimeAccentYellow else CrimeCardBorder, RoundedCornerShape(16.dp))
      .clickable { onClick() }
      .padding(horizontal = 10.dp, vertical = 6.dp)
  ) {
    Text(
      text = label,
      color = if (isSelected) Color.Black else CrimeTextSecondary,
      fontSize = 11.sp,
      fontWeight = FontWeight.Bold
    )
  }
}

@Composable
fun ClueItemCard(
  clue: CrimeSceneClue,
  isUvActive: Boolean,
  onDiscover: () -> Unit,
  onInspectForensics: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("clue_card_${clue.id}"),
    colors = CardDefaults.cardColors(
      containerColor = if (isUvActive && clue.hiddenUvSecret.isNotBlank()) Color(0xFF1B1433) else CrimeCardBg
    ),
    shape = RoundedCornerShape(12.dp),
    border = androidx.compose.foundation.BorderStroke(
      if (isUvActive && clue.hiddenUvSecret.isNotBlank()) 1.5.dp else 1.dp,
      if (isUvActive && clue.hiddenUvSecret.isNotBlank()) Color(0xFFB388FF)
      else if (clue.isDiscovered) CrimeForensicCyan.copy(alpha = 0.5f) else CrimeCardBorder
    )
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          EvidenceBadge(
            label = clue.code,
            color = if (clue.isDiscovered) CrimeForensicCyan else CrimeAccentYellow
          )
          Spacer(modifier = Modifier.width(8.dp))
          EvidenceBadge(label = clue.category.label, color = Color(0xFFBA68C8))
        }

        if (clue.isDiscovered) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.CheckCircle,
              contentDescription = null,
              tint = CrimeForensicCyan,
              modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = "LOGGED",
              color = CrimeForensicCyan,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = clue.name,
        color = CrimeTextPrimary,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold
      )

      // Physical Home Staging Zone & Tip
      Spacer(modifier = Modifier.height(4.dp))
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.Place,
          contentDescription = null,
          tint = CrimeAccentYellow,
          modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = "Physical Zone: ${clue.physicalZone}",
          color = CrimeAccentYellow,
          fontSize = 12.sp,
          fontWeight = FontWeight.SemiBold
        )
      }

      // If this is Clue 1, show our generated photo!
      if (clue.id == "clue_01" && clue.isDiscovered) {
        Spacer(modifier = Modifier.height(10.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, CrimeCardBorder, RoundedCornerShape(8.dp))
        ) {
          Image(
            painter = painterResource(id = R.drawable.evidence_poison_bottle),
            contentDescription = "Forensic Evidence Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
          )
          Box(
            modifier = Modifier
              .align(Alignment.BottomStart)
              .background(Color.Black.copy(alpha = 0.65f))
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text(
              text = "FORENSIC PHOTO: EXHIBIT 1 WINE GLASS & ACONITE VIAL",
              color = CrimeAccentYellow,
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = clue.description,
        color = CrimeTextSecondary,
        fontSize = 12.sp,
        lineHeight = 16.sp
      )

      // UV FLUORESCENCE SECRET BOX
      if (isUvActive && clue.hiddenUvSecret.isNotBlank()) {
        Spacer(modifier = Modifier.height(8.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(Color(0xFF2C1E4A))
            .border(1.dp, Color(0xFFB388FF), RoundedCornerShape(6.dp))
            .padding(10.dp)
        ) {
          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Lightbulb, contentDescription = null, tint = Color(0xFFE040FB), modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "UV FLUORESCENCE DETECTED (365nm):",
                color = Color(0xFFE040FB),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
              )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = clue.hiddenUvSecret,
              color = Color(0xFFB388FF),
              fontSize = 12.sp,
              fontFamily = FontFamily.Monospace,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(6.dp))
          .background(CrimeDarkSurface)
          .padding(8.dp)
      ) {
        Text(
          text = "Host Setup Tip: ${clue.physicalStagingTip}",
          color = CrimeTextSecondary,
          fontSize = 11.sp,
          fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        if (!clue.isDiscovered) {
          Button(
            onClick = onDiscover,
            colors = ButtonDefaults.buttonColors(containerColor = CrimeAccentYellow),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.weight(1f)
          ) {
            Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Mark Discovered", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
          }
        }

        OutlinedButton(
          onClick = onInspectForensics,
          colors = ButtonDefaults.outlinedButtonColors(contentColor = CrimeForensicCyan),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier.weight(1f)
        ) {
          Icon(Icons.Default.Biotech, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("Forensics Lab", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun ForensicsLabView(
  clue: CrimeSceneClue,
  isTested: Boolean,
  isUvActive: Boolean,
  onRunReagentTest: () -> Unit,
  onToggleUv: () -> Unit,
  onClose: () -> Unit
) {
  var isTestingSimulated by remember { mutableStateOf(false) }
  var testProgress by remember { mutableFloatStateOf(0f) }

  LaunchedEffect(isTestingSimulated) {
    if (isTestingSimulated) {
      testProgress = 0f
      while (testProgress < 1f) {
        delay(80)
        testProgress += 0.15f
      }
      isTestingSimulated = false
      onRunReagentTest()
    }
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {
    Card(
      modifier = Modifier.fillMaxWidth(),
      colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
      shape = RoundedCornerShape(14.dp),
      border = androidx.compose.foundation.BorderStroke(1.dp, CrimeForensicCyan)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "FORENSICS LAB ANALYSIS",
            color = CrimeForensicCyan,
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 1.sp
          )
          EvidenceBadge(label = clue.code, color = CrimeForensicCyan)
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
          text = clue.name,
          color = CrimeTextPrimary,
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = "Found in: ${clue.physicalZone}",
          color = CrimeAccentYellow,
          fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(
          text = "Detailed Forensic Observations:",
          color = CrimeTextSecondary,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = clue.forensicDetails,
          color = CrimeTextPrimary,
          fontSize = 13.sp,
          lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        // UV Inspection section
        Card(
          modifier = Modifier.fillMaxWidth(),
          colors = CardDefaults.cardColors(containerColor = if (isUvActive) Color(0xFF24183E) else CrimeDarkSurface),
          shape = RoundedCornerShape(10.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, if (isUvActive) Color(0xFFB388FF) else CrimeCardBorder)
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Lightbulb, contentDescription = null, tint = Color(0xFFB388FF), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "UV BLACKLIGHT SCANNER (365nm)",
                  color = Color(0xFFB388FF),
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold
                )
              }
              OutlinedButton(
                onClick = onToggleUv,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFB388FF)),
                shape = RoundedCornerShape(6.dp)
              ) {
                Text(if (isUvActive) "Deactivate" else "Activate UV", fontSize = 10.sp)
              }
            }

            if (isUvActive && clue.hiddenUvSecret.isNotBlank()) {
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = clue.hiddenUvSecret,
                color = Color(0xFFE040FB),
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Reagent Chemical Test Chamber
        Card(
          modifier = Modifier.fillMaxWidth(),
          colors = CardDefaults.cardColors(containerColor = CrimeDarkSurface),
          shape = RoundedCornerShape(10.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, CrimeCardBorder)
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Science, contentDescription = null, tint = CrimeForensicCyan, modifier = Modifier.size(20.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "CHEMICAL REAGENT REACTION TEST",
                color = CrimeForensicCyan,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (isTestingSimulated) {
              Text(
                text = "Simulating titration drops & spectrometer...",
                color = CrimeAccentYellow,
                fontSize = 12.sp
              )
              Spacer(modifier = Modifier.height(6.dp))
              LinearProgressIndicator(
                progress = { testProgress },
                modifier = Modifier.fillMaxWidth(),
                color = CrimeForensicCyan,
                trackColor = CrimeDarkSurface,
              )
            } else if (isTested) {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(8.dp))
                  .background(CrimeBloodDark.copy(alpha = 0.35f))
                  .border(1.dp, CrimeRedBright, RoundedCornerShape(8.dp))
                  .padding(12.dp)
              ) {
                Column {
                  Text(
                    text = "LAB RESULTS CONFIRMED",
                    color = CrimeAccentYellow,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                  )
                  Spacer(modifier = Modifier.height(4.dp))
                  Text(
                    text = clue.testReagentResult,
                    color = CrimeTextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 18.sp
                  )
                }
              }
            } else {
              Text(
                text = "Apply chemical indicator drops to test for Aconite alkaloids, arsenic traces, or latent fluid.",
                color = CrimeTextSecondary,
                fontSize = 12.sp,
                lineHeight = 16.sp
              )

              Spacer(modifier = Modifier.height(12.dp))

              Button(
                onClick = { isTestingSimulated = true },
                colors = ButtonDefaults.buttonColors(containerColor = CrimeForensicCyan),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("run_lab_test_button")
              ) {
                Icon(Icons.Default.Biotech, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Run Forensic Lab Test", color = Color.Black, fontWeight = FontWeight.Bold)
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
          onClick = onClose,
          colors = ButtonDefaults.buttonColors(containerColor = CrimeDarkSurface),
          shape = RoundedCornerShape(8.dp),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, CrimeCardBorder, RoundedCornerShape(8.dp))
        ) {
          Text("Return to Evidence Catalog", color = CrimeTextSecondary)
        }
      }
    }
  }
}
