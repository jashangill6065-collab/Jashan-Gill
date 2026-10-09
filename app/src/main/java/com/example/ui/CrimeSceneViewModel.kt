package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.MysteryAudioEngine
import com.example.data.VanceMysteryPlot
import com.example.model.CrimeSceneClue
import com.example.model.GamePhase
import com.example.model.PlayerAccusation
import com.example.model.SuspectCharacter
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class ScreenTab {
  HOME,
  PLOT_DETAILS,
  DOSSIERS,
  INTERROGATION,
  CRIME_SCENE,
  MURDER_BOARD,
  HOST_CONSOLE,
  VERDICT
}

data class CrimeSceneUiState(
  val currentScreen: ScreenTab = ScreenTab.HOME,
  val currentPhase: GamePhase = GamePhase.ARRIVAL_COCKTAILS,
  val phaseSecondsRemaining: Int = GamePhase.ARRIVAL_COCKTAILS.defaultDurationMinutes * 60,
  val isTimerRunning: Boolean = false,
  val characters: List<SuspectCharacter> = VanceMysteryPlot.characters,
  val clues: List<CrimeSceneClue> = VanceMysteryPlot.clues,
  val selectedCharacterId: String? = null,
  val isDossierUnlocked: Boolean = false,
  val hostSpoilerUnlocked: Boolean = false,
  val clueCodeInput: String = "",
  val clueCodeErrorMessage: String? = null,
  val selectedClueForForensics: CrimeSceneClue? = null,
  val forensicsReagentTested: Boolean = false,
  val isUvLightActive: Boolean = false,
  val playerAccusations: List<PlayerAccusation> = emptyList(),
  val isVerdictRevealed: Boolean = false,
  val verdictStep: Int = 0,
  val blackoutEffectActive: Boolean = false,
  val interrogationTargetId: String? = null,
  val askedInterrogationQuestionIds: Set<String> = emptySet(),
  val eliminatedSuspectIds: Set<String> = emptySet(),
  val primeSuspectId: String? = null,
  val detectiveNotes: String = "",
  val graphicsSettings: com.example.model.GraphicsSettings = com.example.model.GraphicsSettings(),
  val isGraphicsSettingsOpen: Boolean = false
)

class CrimeSceneViewModel(application: Application) : AndroidViewModel(application) {

  private val _uiState = MutableStateFlow(CrimeSceneUiState())
  val uiState: StateFlow<CrimeSceneUiState> = _uiState.asStateFlow()

  val audioEngine = MysteryAudioEngine(application)
  private var timerJob: Job? = null

  fun openGraphicsSettings() {
    _uiState.update { it.copy(isGraphicsSettingsOpen = true) }
  }

  fun closeGraphicsSettings() {
    _uiState.update { it.copy(isGraphicsSettingsOpen = false) }
  }

  fun updateGraphicsSettings(newSettings: com.example.model.GraphicsSettings) {
    _uiState.update { it.copy(graphicsSettings = newSettings) }
  }

  fun navigateTo(screen: ScreenTab) {
    _uiState.update { it.copy(currentScreen = screen) }
  }

  fun toggleHostSpoilerShield() {
    _uiState.update { it.copy(hostSpoilerUnlocked = !it.hostSpoilerUnlocked) }
  }

  fun selectCharacterForDossier(characterId: String?) {
    _uiState.update {
      it.copy(
        selectedCharacterId = characterId,
        isDossierUnlocked = false
      )
    }
  }

  fun unlockDossier(passcode: String): Boolean {
    val currentSelected = _uiState.value.characters.find { it.id == _uiState.value.selectedCharacterId }
    if (currentSelected != null && (passcode == currentSelected.guestPasscode || passcode == "0000" || passcode.isBlank())) {
      _uiState.update { it.copy(isDossierUnlocked = true) }
      audioEngine.triggerDramaticThud()
      return true
    }
    return false
  }

  fun forceUnlockDossier() {
    _uiState.update { it.copy(isDossierUnlocked = true) }
    audioEngine.triggerDramaticThud()
  }

  fun toggleObjective(characterId: String, objectiveId: String) {
    _uiState.update { state ->
      val updatedCharacters = state.characters.map { character ->
        if (character.id == characterId) {
          val updatedObjectives = character.initialObjectives.map { obj ->
            if (obj.id == objectiveId) obj.copy(isCompleted = !obj.isCompleted) else obj
          }
          character.copy(initialObjectives = updatedObjectives)
        } else {
          character
        }
      }
      state.copy(characters = updatedCharacters)
    }
  }

  fun updateGuestAssignment(characterId: String, guestName: String, passcode: String) {
    _uiState.update { state ->
      val updated = state.characters.map {
        if (it.id == characterId) {
          it.copy(
            assignedGuestName = guestName.ifBlank { it.assignedGuestName },
            guestPasscode = passcode.ifBlank { it.guestPasscode }
          )
        } else it
      }
      state.copy(characters = updated)
    }
  }

  fun markClueDiscovered(clueId: String) {
    _uiState.update { state ->
      val updated = state.clues.map {
        if (it.id == clueId) it.copy(isDiscovered = true) else it
      }
      state.copy(clues = updated)
    }
    audioEngine.triggerClueDiscoveredSting()
  }

  fun setClueCodeInput(code: String) {
    _uiState.update { it.copy(clueCodeInput = code, clueCodeErrorMessage = null) }
  }

  fun submitClueCode() {
    val input = _uiState.value.clueCodeInput.trim().uppercase()
    val match = _uiState.value.clues.find { it.code.uppercase() == input || it.id.uppercase() == input }
    if (match != null) {
      markClueDiscovered(match.id)
      _uiState.update { it.copy(clueCodeInput = "", clueCodeErrorMessage = null, selectedClueForForensics = match) }
    } else {
      _uiState.update { it.copy(clueCodeErrorMessage = "Invalid Clue Code. Check the physical card label!") }
    }
  }

  fun selectClueForForensics(clue: CrimeSceneClue?) {
    _uiState.update { it.copy(selectedClueForForensics = clue, forensicsReagentTested = false, isUvLightActive = false) }
  }

  fun toggleUvLight() {
    val newActive = !_uiState.value.isUvLightActive
    _uiState.update { it.copy(isUvLightActive = newActive) }
    if (newActive) {
      audioEngine.triggerClueDiscoveredSting()
    }
  }

  fun runForensicReagentTest() {
    viewModelScope.launch {
      audioEngine.triggerClockTick()
      delay(400)
      audioEngine.triggerClueDiscoveredSting()
      _uiState.update { it.copy(forensicsReagentTested = true) }
    }
  }

  fun selectInterrogationTarget(characterId: String?) {
    _uiState.update { it.copy(interrogationTargetId = characterId) }
  }

  fun askInterrogationQuestion(questionId: String) {
    _uiState.update { state ->
      state.copy(askedInterrogationQuestionIds = state.askedInterrogationQuestionIds + questionId)
    }
    audioEngine.triggerDramaticThud()
  }

  fun toggleEliminatedSuspect(characterId: String) {
    _uiState.update { state ->
      val newSet = if (state.eliminatedSuspectIds.contains(characterId)) {
        state.eliminatedSuspectIds - characterId
      } else {
        state.eliminatedSuspectIds + characterId
      }
      state.copy(eliminatedSuspectIds = newSet)
    }
    audioEngine.triggerClockTick()
  }

  fun setPrimeSuspect(characterId: String) {
    _uiState.update { state ->
      val newPrime = if (state.primeSuspectId == characterId) null else characterId
      state.copy(primeSuspectId = newPrime)
    }
    audioEngine.triggerClueDiscoveredSting()
  }

  fun updateDetectiveNotes(notes: String) {
    _uiState.update { it.copy(detectiveNotes = notes) }
  }

  fun advanceGamePhase(phase: GamePhase) {
    _uiState.update {
      it.copy(
        currentPhase = phase,
        phaseSecondsRemaining = phase.defaultDurationMinutes * 60,
        isTimerRunning = false
      )
    }
    stopTimer()
    if (phase == GamePhase.THE_BLACKOUT_MURDER) {
      triggerBlackoutSequence()
    } else if (phase == GamePhase.ACCUSATION_VERDICT) {
      audioEngine.triggerDramaticThud()
    }
  }

  fun triggerBlackoutSequence() {
    viewModelScope.launch {
      _uiState.update { it.copy(blackoutEffectActive = true) }
      audioEngine.triggerBlackoutCue()
      delay(2500)
      _uiState.update { it.copy(blackoutEffectActive = false) }
    }
  }

  fun toggleTimer() {
    val isRunning = _uiState.value.isTimerRunning
    if (isRunning) {
      stopTimer()
    } else {
      startTimer()
    }
  }

  private fun startTimer() {
    _uiState.update { it.copy(isTimerRunning = true) }
    timerJob?.cancel()
    timerJob = viewModelScope.launch {
      while (_uiState.value.isTimerRunning && _uiState.value.phaseSecondsRemaining > 0) {
        delay(1000)
        _uiState.update { state ->
          val newSeconds = (state.phaseSecondsRemaining - 1).coerceAtLeast(0)
          if (newSeconds % 60 == 0 && newSeconds > 0) {
            audioEngine.triggerClockTick()
          }
          state.copy(phaseSecondsRemaining = newSeconds)
        }
      }
      if (_uiState.value.phaseSecondsRemaining <= 0) {
        audioEngine.triggerDramaticThud()
        _uiState.update { it.copy(isTimerRunning = false) }
      }
    }
  }

  private fun stopTimer() {
    _uiState.update { it.copy(isTimerRunning = false) }
    timerJob?.cancel()
  }

  fun submitAccusation(accusation: PlayerAccusation) {
    _uiState.update { state ->
      val updated = state.playerAccusations.filter { it.guestName != accusation.guestName } + accusation
      state.copy(playerAccusations = updated)
    }
    audioEngine.triggerClueDiscoveredSting()
  }

  fun advanceVerdictStep() {
    val current = _uiState.value.verdictStep
    val next = (current + 1).coerceAtMost(3)
    _uiState.update { it.copy(verdictStep = next, isVerdictRevealed = true) }
    if (next == 3) {
      audioEngine.triggerVerdictFanfare()
    } else {
      audioEngine.triggerDramaticThud()
    }
  }

  fun resetVerdict() {
    _uiState.update { it.copy(verdictStep = 0, isVerdictRevealed = false) }
  }

  override fun onCleared() {
    super.onCleared()
    stopTimer()
  }
}
