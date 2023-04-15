package dev.bogwalk.ui.integration

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.model.GameGrid
import dev.bogwalk.model.TestLevelGenerator
import dev.bogwalk.ui.*
import dev.bogwalk.ui.assertGameScreenDisabled
import dev.bogwalk.ui.performClickToPlay
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.VoltorbFlipAppState
import org.junit.Rule
import kotlin.test.Test

class GamePlayTest {
    companion object {
        private val tester = TestLevelGenerator().apply {
            toAssign = listOf(0, 7, 9, 24, 1, 4, 12, 14, 15, 21)
        }
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `VoltorbFlipApp game clear follows expected sequence`() {
        composeTestRule.setContent {
            VoltorbFlipApp(VoltorbFlipAppState(GameGrid(tester)))
        }

        composeTestRule.performClickToPlay()

        composeTestRule.waitForIdle()

        // flip all x2
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()[0].performClick()
        composeTestRule.waitForIdle()
        // click away pause speech box
        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .assertIsEnabled().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()[8].assertIsEnabled().performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()[10].assertIsEnabled().performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filter(hasContentDescriptionExactly("${FLIPPED_DESCR}2"))
            .assertCountEquals(3)

        // flip single x3
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()[28].assertIsEnabled().performClick()
        composeTestRule.waitForIdle()

        // last selected should be flipped, but no more
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filter(hasContentDescription(FLIPPED_DESCR, substring = true))
            .assertCountEquals(4)
        // game won screen should start over disabled grid
        composeTestRule.assertGameScreenDisabled()
        composeTestRule.onSpeechBox(GAME_CLEAR_START.substringBefore("|"))
            .performClick()

        composeTestRule.waitForIdle()

        // iterate through game won speech
        repeat(2) {
            composeTestRule.onSpeechBox()
                .performClick()
            composeTestRule.waitForIdle()
        }

        // coins should move to total once last line is displayed
        composeTestRule.onAllNodesWithTag(COIN_TAG)
            .filterToOne(hasAnyChild(hasTextExactly(PLAYER_COINS, "00024")))
        composeTestRule.onSpeechBox("${GAME_CLEAR_START.takeLast(16)}24$GAME_CLEAR_END")
            .performClick()

        composeTestRule.waitForIdle()

        // all tiles should be flipped
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filter(hasContentDescriptionExactly("${FLIPPED_DESCR}0"))
            .assertCountEquals(6)
        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .assertIsEnabled()
            .performClick()

        composeTestRule.waitForIdle()

        // grid should be cleared of data & level advanced
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filter(hasContentDescriptionExactly(INFO_ZERO_DESCR))
            .assertCountEquals(10)
            .assertAll(hasTextExactly("00", "0"))
        composeTestRule.onSpeechBox("${ADVANCE_START}2${ADVANCE_END.substringBefore("|")}")
            .performClick()

        composeTestRule.waitForIdle()

        // iterate through advance speech
        repeat(2) {
            composeTestRule.onSpeechBox()
                .performClick()
            composeTestRule.waitForIdle()
        }

        // should be in pre-game now
        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(3)
    }

    @Test
    fun `VoltorbFlipApp shows speech for newly flipped multiples`() {
        composeTestRule.setContent {
            VoltorbFlipApp(VoltorbFlipAppState(GameGrid(tester)))
        }

        composeTestRule.performClickToPlay()

        composeTestRule.waitForIdle()

        // flip first x2
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()[0].performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filterToOne(hasContentDescriptionExactly("${FLIPPED_DESCR}2"))
        composeTestRule.onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .onChildren()
            .assertAny(hasTextExactly("${NEW_1}2${NEW_2}2$NEW_3"))
        composeTestRule.assertGameScreenDisabled()

        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .performClick()

        composeTestRule.waitForIdle()

        // flip first x3
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()[28].performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filterToOne(hasContentDescriptionExactly("${FLIPPED_DESCR}3"))
        composeTestRule.onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .onChildren()
            .assertAny(hasTextExactly("${NEW_1}3${NEW_2}3$NEW_3"))
        composeTestRule.assertGameScreenDisabled()

        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .performClick()

        composeTestRule.waitForIdle()

        // flip a second X2
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()[8].performClick()

        composeTestRule.waitForIdle()

        // nothing should change except flipped tile
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filter(hasContentDescriptionExactly("${FLIPPED_DESCR}2"))
            .assertCountEquals(2)
        composeTestRule.onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .assertDoesNotExist()
        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .assertDoesNotExist()
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filter(isEnabled())
            .assertCountEquals(22)
    }
}