package com.example

import com.example.data.VanceMysteryPlot
import com.example.model.GraphicsSettings
import com.example.model.VisualThemeMode
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun mysteryPlot_definesVictimAndCauseOfDeath() {
    val victim = VanceMysteryPlot.victim
    assertEquals("Lord Alistair Vance", victim.name)
    assertEquals(64, victim.age)
    assertTrue(victim.background.isNotBlank())
    assertTrue(victim.foundLocation.isNotBlank())

    val cause = VanceMysteryPlot.causeOfDeath
    assertTrue(cause.primaryCause.contains("Aconite", ignoreCase = true))
    assertTrue(cause.stagedSecondaryWeapon.contains("poker", ignoreCase = true))
    assertTrue(cause.coronerFindings.isNotBlank())
  }

  @Test
  fun mysteryPlot_definesMurdererAndMotive() {
    val murderer = VanceMysteryPlot.murderer
    assertEquals("Dr. Julian Ward", murderer.characterName)
    assertEquals("dr_ward", murderer.characterId)
    assertTrue(murderer.fullMotive.contains("3.2 million", ignoreCase = true))
    assertTrue(murderer.confessionStatement.isNotBlank())
  }

  @Test
  fun mysteryPlot_definesTimelineAndAlibis() {
    val timeline = VanceMysteryPlot.timelineEvents
    assertTrue("Should have at least 5 timeline events leading up to murder", timeline.size >= 5)

    val characters = VanceMysteryPlot.characters
    assertTrue("Should have at least 4 characters with detailed alibis", characters.size >= 4)

    // Check each character has an alibi statement and flaw
    characters.forEach { character ->
      assertTrue(character.alibiStatement.isNotBlank())
      assertTrue(character.alibiFlaw.isNotBlank())
      assertTrue(character.secretDarkMotive.isNotBlank())
      assertTrue(character.initialObjectives.isNotEmpty())
    }
  }

  @Test
  fun mysteryPlot_definesMajorTwists() {
    val twists = VanceMysteryPlot.plotTwists
    assertTrue("Should include at least 3 major plot twists", twists.size >= 3)
    assertTrue(twists.any { it.title.contains("Double Poison", ignoreCase = true) })
    assertTrue(twists.any { it.title.contains("Staged", ignoreCase = true) })
    assertTrue(twists.any { it.title.contains("Dying", ignoreCase = true) })
  }

  @Test
  fun playableInterrogationAndUv_areConfigured() {
    val characters = VanceMysteryPlot.characters
    val suspectsWithQuestions = characters.filter { it.interrogationQuestions.isNotEmpty() }
    assertTrue("Suspects should have interactive interrogation dialogues", suspectsWithQuestions.size >= 4)

    val clues = VanceMysteryPlot.clues
    val uvClues = clues.filter { it.hiddenUvSecret.isNotBlank() }
    assertTrue("Clues should have hidden UV fluorescence secrets", uvClues.size >= 5)
  }

  @Test
  fun graphicsSettings_defaultsAreValid() {
    val settings = GraphicsSettings()
    assertEquals(VisualThemeMode.MIDNIGHT_NOIR, settings.themeMode)
    assertTrue(settings.ambientBrightness in 0.7f..1.3f)
    assertTrue(settings.cinematicVignetteEnabled)
  }
}
