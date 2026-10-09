package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DisplaySettings
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.MotionSmoothness
import com.example.model.VisualThemeMode
import com.example.ui.CrimeSceneUiState
import com.example.ui.CrimeSceneViewModel
import com.example.ui.ScreenTab
import com.example.ui.components.GraphicsAdjustDialog
import com.example.ui.screens.AccusationRevealScreen
import com.example.ui.screens.CrimeSceneScreen
import com.example.ui.screens.GuestDossierScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.HostGameMasterScreen
import com.example.ui.screens.InterrogationScreen
import com.example.ui.screens.MurderBoardScreen
import com.example.ui.screens.PlotDetailsScreen
import com.example.ui.theme.CrimeAccentYellow
import com.example.ui.theme.CrimeBloodDark
import com.example.ui.theme.CrimeCardBg
import com.example.ui.theme.CrimeDarkSurface
import com.example.ui.theme.CrimeForensicCyan
import com.example.ui.theme.CrimeRed
import com.example.ui.theme.CrimeRedBright
import com.example.ui.theme.CrimeTextPrimary
import com.example.ui.theme.CrimeTextSecondary
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        CrimeSceneApp()
      }
    }
  }
}

@Composable
fun CrimeSceneApp(
  viewModel: CrimeSceneViewModel = viewModel()
) {
  val uiState by viewModel.uiState.collectAsState()

  // Handle hardware back press gracefully
  BackHandler(enabled = uiState.currentScreen != ScreenTab.HOME) {
    viewModel.navigateTo(ScreenTab.HOME)
  }

  // Graphics adjustments overlay modifiers
  val ambientBrightness = uiState.graphicsSettings.ambientBrightness.coerceIn(0.65f, 1.3f)
  val themeFilterColor = when (uiState.graphicsSettings.themeMode) {
    VisualThemeMode.CLASSIC_FILM_NOIR -> Color(0x22111111)
    VisualThemeMode.VINTAGE_SEPIA -> Color(0x18795548)
    VisualThemeMode.CRIME_OLED -> Color.Black.copy(alpha = 0.05f)
    VisualThemeMode.MIDNIGHT_NOIR -> Color.Transparent
  }

  Box(modifier = Modifier.fillMaxSize()) {
    Scaffold(
      contentWindowInsets = WindowInsets.safeDrawing,
      bottomBar = {
        CrimeSceneBottomBar(
          currentScreen = uiState.currentScreen,
          onNavigate = { viewModel.navigateTo(it) }
        )
      }
    ) { innerPadding ->
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
          .graphicsLayer {
            // Apply ambient brightness adjustments smoothly
            alpha = if (ambientBrightness > 1.0f) 1.0f else ambientBrightness
          }
      ) {
        // Silky smooth screen transition animation
        AnimatedContent(
          targetState = uiState.currentScreen,
          transitionSpec = {
            when (uiState.graphicsSettings.motionSmoothness) {
              MotionSmoothness.REDUCED_MOTION -> {
                fadeIn(animationSpec = snap()).togetherWith(fadeOut(animationSpec = snap()))
              }
              else -> {
                (fadeIn(animationSpec = tween(220)) + scaleIn(initialScale = 0.985f, animationSpec = tween(220)))
                  .togetherWith(fadeOut(animationSpec = tween(180)))
              }
            }
          },
          label = "ScreenTransition"
        ) { screen ->
          when (screen) {
            ScreenTab.HOME -> {
              HomeScreen(
                uiState = uiState,
                onNavigate = { viewModel.navigateTo(it) },
                onAdvancePhase = {
                  val currentIdx = uiState.currentPhase.ordinal
                  val nextIdx = (currentIdx + 1).coerceAtMost(com.example.model.GamePhase.values().size - 1)
                  viewModel.advanceGamePhase(com.example.model.GamePhase.values()[nextIdx])
                },
                onOpenGraphicsSettings = { viewModel.openGraphicsSettings() }
              )
            }

            ScreenTab.PLOT_DETAILS -> {
              PlotDetailsScreen(
                uiState = uiState,
                onToggleSpoiler = { viewModel.toggleHostSpoilerShield() },
                onBack = { viewModel.navigateTo(ScreenTab.HOME) }
              )
            }

            ScreenTab.DOSSIERS -> {
              GuestDossierScreen(
                uiState = uiState,
                onSelectCharacter = { viewModel.selectCharacterForDossier(it) },
                onUnlockDossier = { viewModel.unlockDossier(it) },
                onForceUnlock = { viewModel.forceUnlockDossier() },
                onToggleObjective = { charId, objId -> viewModel.toggleObjective(charId, objId) },
                onBack = { viewModel.navigateTo(ScreenTab.HOME) }
              )
            }

            ScreenTab.INTERROGATION -> {
              InterrogationScreen(
                uiState = uiState,
                onSelectSuspect = { viewModel.selectInterrogationTarget(it) },
                onAskQuestion = { viewModel.askInterrogationQuestion(it) },
                onBack = { viewModel.navigateTo(ScreenTab.HOME) }
              )
            }

            ScreenTab.CRIME_SCENE -> {
              CrimeSceneScreen(
                uiState = uiState,
                onClueCodeChange = { viewModel.setClueCodeInput(it) },
                onSubmitClueCode = { viewModel.submitClueCode() },
                onMarkClueDiscovered = { viewModel.markClueDiscovered(it) },
                onSelectClueForForensics = { viewModel.selectClueForForensics(it) },
                onRunReagentTest = { viewModel.runForensicReagentTest() },
                onToggleUvLight = { viewModel.toggleUvLight() },
                onBack = { viewModel.navigateTo(ScreenTab.HOME) }
              )
            }

            ScreenTab.MURDER_BOARD -> {
              MurderBoardScreen(
                uiState = uiState,
                onToggleEliminated = { viewModel.toggleEliminatedSuspect(it) },
                onSetPrimeSuspect = { viewModel.setPrimeSuspect(it) },
                onUpdateNotes = { viewModel.updateDetectiveNotes(it) },
                onBack = { viewModel.navigateTo(ScreenTab.HOME) }
              )
            }

            ScreenTab.HOST_CONSOLE -> {
              HostGameMasterScreen(
                uiState = uiState,
                onAdvancePhase = { viewModel.advanceGamePhase(it) },
                onToggleTimer = { viewModel.toggleTimer() },
                onTriggerBlackout = { viewModel.triggerBlackoutSequence() },
                onPlaySound = { sound ->
                  when (sound) {
                    "thud" -> viewModel.audioEngine.triggerDramaticThud()
                    "clock" -> viewModel.audioEngine.triggerClockTick()
                    "chime" -> viewModel.audioEngine.triggerClueDiscoveredSting()
                    "fanfare" -> viewModel.audioEngine.triggerVerdictFanfare()
                  }
                },
                onUpdateGuest = { charId, name, pin -> viewModel.updateGuestAssignment(charId, name, pin) },
                onBack = { viewModel.navigateTo(ScreenTab.HOME) }
              )
            }

            ScreenTab.VERDICT -> {
              AccusationRevealScreen(
                uiState = uiState,
                onSubmitAccusation = { viewModel.submitAccusation(it) },
                onAdvanceVerdictStep = { viewModel.advanceVerdictStep() },
                onResetVerdict = { viewModel.resetVerdict() },
                onBack = { viewModel.navigateTo(ScreenTab.HOME) }
              )
            }
          }
        }

        // FULL SCREEN DRAMATIC BLACKOUT OVERLAY
        val flashIntensity = uiState.graphicsSettings.blackoutFlashIntensity
        AnimatedVisibility(
          visible = uiState.blackoutEffectActive && flashIntensity > 0f,
          enter = fadeIn(animationSpec = tween(150)),
          exit = fadeOut(animationSpec = tween(400)),
          modifier = Modifier.fillMaxSize()
        ) {
          Box(
            modifier = Modifier
              .fillMaxSize()
              .background(Color.Black.copy(alpha = flashIntensity)),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Icon(
                imageVector = Icons.Default.FlashOn,
                contentDescription = null,
                tint = CrimeRedBright,
                modifier = Modifier.size(54.dp)
              )
              Spacer(modifier = Modifier.height(16.dp))
              Text(
                text = "ESTATE BLACKOUT!",
                color = CrimeRedBright,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp,
                fontFamily = FontFamily.Monospace
              )
              Spacer(modifier = Modifier.height(8.dp))
              Text(
                text = "The generator has blown. Someone is in the dark...",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 13.sp
              )
            }
          }
        }
      }
    }

    // CINEMATIC VIGNETTE OVERLAY
    if (uiState.graphicsSettings.cinematicVignetteEnabled) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(
            brush = Brush.radialGradient(
              colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.38f)),
              radius = 900f
            )
          )
      )
    }

    // ATMOSPHERIC TONE FILTER OVERLAY
    if (themeFilterColor != Color.Transparent) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(themeFilterColor)
      )
    }

    // GRAPHICS SETTINGS DIALOG
    if (uiState.isGraphicsSettingsOpen) {
      GraphicsAdjustDialog(
        settings = uiState.graphicsSettings,
        onUpdateSettings = { viewModel.updateGraphicsSettings(it) },
        onDismiss = { viewModel.closeGraphicsSettings() }
      )
    }
  }
}

@Composable
fun CrimeSceneBottomBar(
  currentScreen: ScreenTab,
  onNavigate: (ScreenTab) -> Unit
) {
  NavigationBar(
    containerColor = CrimeDarkSurface,
    tonalElevation = 8.dp,
    modifier = Modifier
      .testTag("crime_scene_bottom_nav")
  ) {
    NavigationBarItem(
      selected = currentScreen == ScreenTab.HOME,
      onClick = { onNavigate(ScreenTab.HOME) },
      icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
      label = { Text("Home", fontSize = 10.sp) },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.Black,
        selectedTextColor = CrimeAccentYellow,
        indicatorColor = CrimeAccentYellow,
        unselectedIconColor = CrimeTextSecondary,
        unselectedTextColor = CrimeTextSecondary
      )
    )

    NavigationBarItem(
      selected = currentScreen == ScreenTab.INTERROGATION,
      onClick = { onNavigate(ScreenTab.INTERROGATION) },
      icon = { Icon(Icons.Default.Psychology, contentDescription = "Interrogate") },
      label = { Text("Interrogate", fontSize = 10.sp) },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.White,
        selectedTextColor = CrimeRedBright,
        indicatorColor = CrimeRed,
        unselectedIconColor = CrimeTextSecondary,
        unselectedTextColor = CrimeTextSecondary
      )
    )

    NavigationBarItem(
      selected = currentScreen == ScreenTab.DOSSIERS,
      onClick = { onNavigate(ScreenTab.DOSSIERS) },
      icon = { Icon(Icons.Default.Fingerprint, contentDescription = "Dossiers") },
      label = { Text("Dossiers", fontSize = 10.sp) },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.Black,
        selectedTextColor = CrimeAccentYellow,
        indicatorColor = CrimeAccentYellow,
        unselectedIconColor = CrimeTextSecondary,
        unselectedTextColor = CrimeTextSecondary
      )
    )

    NavigationBarItem(
      selected = currentScreen == ScreenTab.CRIME_SCENE,
      onClick = { onNavigate(ScreenTab.CRIME_SCENE) },
      icon = { Icon(Icons.Default.Search, contentDescription = "Clues") },
      label = { Text("Clues", fontSize = 10.sp) },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.Black,
        selectedTextColor = CrimeForensicCyan,
        indicatorColor = CrimeForensicCyan,
        unselectedIconColor = CrimeTextSecondary,
        unselectedTextColor = CrimeTextSecondary
      )
    )

    NavigationBarItem(
      selected = currentScreen == ScreenTab.MURDER_BOARD,
      onClick = { onNavigate(ScreenTab.MURDER_BOARD) },
      icon = { Icon(Icons.Default.MenuBook, contentDescription = "Board") },
      label = { Text("Board", fontSize = 10.sp) },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.White,
        selectedTextColor = Color(0xFFFF8A80),
        indicatorColor = CrimeBloodDark,
        unselectedIconColor = CrimeTextSecondary,
        unselectedTextColor = CrimeTextSecondary
      )
    )

    NavigationBarItem(
      selected = currentScreen == ScreenTab.VERDICT,
      onClick = { onNavigate(ScreenTab.VERDICT) },
      icon = { Icon(Icons.Default.Gavel, contentDescription = "Verdict") },
      label = { Text("Verdict", fontSize = 10.sp) },
      colors = NavigationBarItemDefaults.colors(
        selectedIconColor = Color.Black,
        selectedTextColor = CrimeRedBright,
        indicatorColor = CrimeAccentYellow,
        unselectedIconColor = CrimeTextSecondary,
        unselectedTextColor = CrimeTextSecondary
      )
    )
  }
}
