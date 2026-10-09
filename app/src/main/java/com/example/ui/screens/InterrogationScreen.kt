package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.InterrogationQuestion
import com.example.model.SuspectCharacter
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

@Composable
fun InterrogationScreen(
  uiState: CrimeSceneUiState,
  onSelectSuspect: (String?) -> Unit,
  onAskQuestion: (String) -> Unit,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  val selectedSuspect = uiState.characters.find { it.id == uiState.interrogationTargetId }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CrimeBackground)
  ) {
    CrimeTapeHeader(text = "FORMAL INTERROGATION ROOM // SUSPECT CONFRONTATION")

    // Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(
        onClick = {
          if (selectedSuspect != null) onSelectSuspect(null) else onBack()
        },
        modifier = Modifier.testTag("interrogation_back_button")
      ) {
        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CrimeTextPrimary)
      }
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = if (selectedSuspect != null) "Interrogating: ${selectedSuspect.name}" else "Interrogation Room",
          color = CrimeTextPrimary,
          fontSize = 17.sp,
          fontWeight = FontWeight.Bold
        )
        Text(
          text = if (selectedSuspect != null) selectedSuspect.roleTitle else "Confront suspects with critical questions",
          color = CrimeAccentYellow,
          fontSize = 11.sp
        )
      }
      if (selectedSuspect != null) {
        EvidenceBadge(label = "QUESTIONING", color = CrimeRedBright)
      }
    }

    HorizontalDivider(color = CrimeCardBorder, thickness = 1.dp)

    if (selectedSuspect == null) {
      // Pick who to interrogate
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
            border = androidx.compose.foundation.BorderStroke(1.dp, CrimeAccentYellow.copy(alpha = 0.5f))
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Text(
                text = "CONFRONT SUSPECTS WITH CLUES & ALIBIS",
                color = CrimeAccentYellow,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Select any character to put them in the hot seat. Ask tough questions to expose nervous body language tells and uncover critical alibi discrepancies!",
                color = CrimeTextSecondary,
                fontSize = 12.sp,
                lineHeight = 16.sp
              )
            }
          }
          Spacer(modifier = Modifier.height(4.dp))
        }

        items(uiState.characters) { suspect ->
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .bouncyClickable { onSelectSuspect(suspect.id) }
              .testTag("interrogate_suspect_${suspect.id}"),
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
                  .border(1.dp, CrimeAccentYellow.copy(alpha = 0.5f), CircleShape),
                contentAlignment = Alignment.Center
              ) {
                Icon(Icons.Default.Person, contentDescription = null, tint = CrimeAccentYellow, modifier = Modifier.size(24.dp))
              }

              Spacer(modifier = Modifier.width(14.dp))

              Column(modifier = Modifier.weight(1f)) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Text(
                    text = suspect.name,
                    color = CrimeTextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                  )
                  EvidenceBadge(label = suspect.alias, color = CrimeForensicCyan)
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = suspect.roleTitle,
                  color = CrimeAccentYellow,
                  fontSize = 11.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "${suspect.interrogationQuestions.size} Direct Questions Available",
                  color = CrimeTextSecondary,
                  fontSize = 11.sp
                )
              }
            }
          }
        }
      }
    } else {
      // Active Interrogation hot seat
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        item {
          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CrimeCardBg),
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, CrimeRed)
          ) {
            Column(modifier = Modifier.padding(16.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(
                    text = selectedSuspect.name,
                    color = CrimeTextPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black
                  )
                  Text(
                    text = selectedSuspect.roleTitle,
                    color = CrimeAccentYellow,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                  )
                }
                EvidenceBadge(label = selectedSuspect.alias, color = CrimeRedBright)
              }

              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = "Official Alibi on Record:",
                color = CrimeTextSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
              )
              Text(
                text = selectedSuspect.alibiStatement,
                color = CrimeTextPrimary,
                fontSize = 12.sp,
                fontFamily = FontFamily.Serif,
                lineHeight = 16.sp
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "CHOOSE A QUESTION TO CONFRONT THEM WITH:",
            color = CrimeAccentYellow,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
          )
        }

        items(selectedSuspect.interrogationQuestions) { q ->
          val isAsked = uiState.askedInterrogationQuestionIds.contains(q.id)

          Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
              containerColor = if (isAsked) CrimeDarkSurface else CrimeCardBg
            ),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(
              1.dp,
              if (isAsked) CrimeForensicCyan.copy(alpha = 0.6f) else CrimeCardBorder
            )
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = if (isAsked) Icons.Default.CheckCircle else Icons.Default.HelpOutline,
                  contentDescription = null,
                  tint = if (isAsked) CrimeForensicCyan else CrimeAccentYellow,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = q.questionPrompt,
                  color = CrimeTextPrimary,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.weight(1f)
                )
              }

              if (!isAsked) {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                  onClick = { onAskQuestion(q.id) },
                  colors = ButtonDefaults.buttonColors(containerColor = CrimeRed),
                  shape = RoundedCornerShape(8.dp),
                  modifier = Modifier
                    .fillMaxWidth()
                    .testTag("ask_question_${q.id}")
                ) {
                  Icon(Icons.Default.Psychology, contentDescription = null, modifier = Modifier.size(16.dp))
                  Spacer(modifier = Modifier.width(6.dp))
                  Text("Confront Suspect", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
              } else {
                // Show Answer and Tell
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                  modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(CrimeCardBg)
                    .border(1.dp, CrimeCardBorder, RoundedCornerShape(8.dp))
                    .padding(10.dp)
                ) {
                  Column {
                    Text(
                      text = "${selectedSuspect.name}'s Response:",
                      color = CrimeAccentYellow,
                      fontSize = 10.sp,
                      fontWeight = FontWeight.Bold,
                      letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                      text = q.suspectResponse,
                      color = CrimeTextPrimary,
                      fontSize = 12.sp,
                      fontFamily = FontFamily.Serif,
                      lineHeight = 16.sp
                    )
                  }
                }

                Spacer(modifier = Modifier.height(8.dp))
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
                      text = "NERVOUS BODY LANGUAGE TELL:",
                      color = CrimeRedBright,
                      fontSize = 10.sp,
                      fontWeight = FontWeight.Bold,
                      letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                      text = q.bodyLanguageTell,
                      color = CrimeTextPrimary,
                      fontSize = 12.sp,
                      fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                      text = "DETECTIVE REVELATION: ${q.revealsClueHint}",
                      color = CrimeForensicCyan,
                      fontSize = 11.sp,
                      fontWeight = FontWeight.SemiBold
                    )
                  }
                }
              }
            }
          }
        }

        item {
          Spacer(modifier = Modifier.height(12.dp))
          Button(
            onClick = { onSelectSuspect(null) },
            colors = ButtonDefaults.buttonColors(containerColor = CrimeDarkSurface),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
              .fillMaxWidth()
              .border(1.dp, CrimeCardBorder, RoundedCornerShape(8.dp))
          ) {
            Text("Interrogate Another Suspect", color = CrimeTextSecondary)
          }
          Spacer(modifier = Modifier.height(24.dp))
        }
      }
    }
  }
}
