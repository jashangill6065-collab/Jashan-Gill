package com.example.model

enum class GamePhase(
  val phaseNumber: Int,
  val title: String,
  val subtitle: String,
  val defaultDurationMinutes: Int,
  val hostInstruction: String
) {
  ARRIVAL_COCKTAILS(
    phaseNumber = 1,
    title = "Act I: The Gathering",
    subtitle = "Cocktails, Mingling & Secret Motives",
    defaultDurationMinutes = 35,
    hostInstruction = "Serve drinks. Guests stay in character, exchange polite gossip, and subtly probe each other's secrets before Lord Vance's announcement."
  ),
  THE_BLACKOUT_MURDER(
    phaseNumber = 2,
    title = "Act II: The Blackout",
    subtitle = "The Murder is Discovered!",
    defaultDurationMinutes = 15,
    hostInstruction = "Trigger the blackout! Dim the lights, play the thunder & scream sound effect. Direct guests to the crime scene where Lord Vance's body is found."
  ),
  INVESTIGATION(
    phaseNumber = 3,
    title = "Act III: The Investigation",
    subtitle = "Search the House & Interrogate",
    defaultDurationMinutes = 60,
    hostInstruction = "Guests search the physical house and backyard zones for hidden clue cards, analyze forensic evidence, and aggressively interrogate alibis."
  ),
  TWIST_EVIDENCE_DROP(
    phaseNumber = 4,
    title = "Act IV: The Second Twist",
    subtitle = "Toxicology Report & Blackmail Drop",
    defaultDurationMinutes = 25,
    hostInstruction = "Release the Coroner's toxicology report. Major plot twists are revealed—suspicions flip as players realize not everything was as it seemed."
  ),
  ACCUSATION_VERDICT(
    phaseNumber = 5,
    title = "Act V: The Grand Reveal",
    subtitle = "Final Accusations & Confession",
    defaultDurationMinutes = 20,
    hostInstruction = "Gather all suspects in the parlor. Each guest submits their final accusation for Murderer, Weapon, and Motive before the final dramatic reveal."
  )
}

enum class EvidenceCategory(val label: String, val iconName: String) {
  WEAPON("Murder Weapon", "Gavel"),
  TOXIN("Toxin & Chemical", "Science"),
  DOCUMENT("Secret Document", "Description"),
  TRACE("Forensic Trace", "Search"),
  PERSONAL("Personal Effect", "Watch")
}

data class ObjectiveItem(
  val id: String,
  val description: String,
  val isCompleted: Boolean = false,
  val rewardHint: String = ""
)

data class InterrogationQuestion(
  val id: String,
  val questionPrompt: String,
  val suspectResponse: String,
  val bodyLanguageTell: String,
  val revealsClueHint: String
)

data class SuspectCharacter(
  val id: String,
  val name: String,
  val alias: String,
  val age: Int,
  val roleTitle: String,
  val attireSuggestion: String,
  val publicBio: String,
  val relationshipToVictim: String,
  val secretDarkMotive: String,
  val whatTheyHide: String,
  val alibiStatement: String,
  val alibiFlaw: String,
  val initialObjectives: List<ObjectiveItem>,
  val gossipKnown: List<String>,
  val interrogationQuestions: List<InterrogationQuestion> = emptyList(),
  val suspicionRating: Int, // 1 to 5
  var assignedGuestName: String = "",
  var guestPasscode: String = "1234"
)

data class CrimeSceneClue(
  val id: String,
  val code: String, // e.g. "CLUE-01"
  val name: String,
  val category: EvidenceCategory,
  val physicalZone: String, // e.g. "Backyard Patio", "Kitchen", "Living Room"
  val physicalStagingTip: String,
  val description: String,
  val forensicDetails: String,
  val pointsToSuspectId: String?,
  val isDiscovered: Boolean = false,
  val testReagentResult: String = "",
  val hiddenUvSecret: String = "" // Revealed when UV flashlight is activated
)

data class VictimProfile(
  val name: String,
  val age: Int,
  val title: String,
  val background: String,
  val foundLocation: String,
  val physicalAppearance: String,
  val lastKnownWords: String
)

data class CauseOfDeathProfile(
  val primaryCause: String,
  val actualWeaponOrToxin: String,
  val stagedSecondaryWeapon: String,
  val coronerFindings: String,
  val toxicologyReport: String
)

data class MurdererProfile(
  val characterId: String,
  val characterName: String,
  val fullMotive: String,
  val premeditationDetails: String,
  val fatalMistake: String,
  val confessionStatement: String
)

data class TimelineEvent(
  val time: String,
  val title: String,
  val description: String,
  val involvedCharacters: List<String>,
  val location: String
)

data class PlotTwist(
  val number: Int,
  val title: String,
  val summary: String,
  val howPlayersDiscoverIt: String,
  val narrativeImpact: String
)

data class PlayerAccusation(
  val guestName: String,
  val accusedCharacterId: String,
  val chosenWeapon: String,
  val chosenMotive: String,
  val reasoning: String
)
