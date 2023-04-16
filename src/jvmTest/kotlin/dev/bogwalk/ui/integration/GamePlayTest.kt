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
        private val testerLv1 = TestLevelGenerator().apply {
            toAssign = listOf(0, 7, 9, 24, 1, 4, 12, 14, 15, 21)
        }
        private val testerLv5 = TestLevelGenerator().apply {
            toAssign = listOf(0, 1, 2, 3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19)
        }
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `VoltorbFlipApp game clear follows expected sequence`() {
        composeTestRule.setContent {
            VoltorbFlipApp(VoltorbFlipAppState(GameGrid(testerLv1)))
        }

        composeTestRule.performClickToPlay()

        composeTestRule.waitForIdle()

        // flip all x2
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()[0].performClick()
        composeTestRule.waitForIdle()
        // click away pause speech box
        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()[8].performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()[10].performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filter(hasContentDescriptionExactly("${FLIPPED_DESCR}2"))
            .assertCountEquals(3)

        // flip single x3 (game won will take precedence over multiplier screen)
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()[28].performClick()
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
            .performClick()

        composeTestRule.waitForIdle()

        // grid should be cleared of data & level advanced
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filter(hasContentDescriptionExactly(INFO_ZERO_DESCR))
            .assertCountEquals(10)
            .assertAll(hasTextExactly("00", "0"))
        composeTestRule.onNodeWithText("${HEADER_START}2$HEADER_END")
            .assertExists()
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
            VoltorbFlipApp(VoltorbFlipAppState(GameGrid(testerLv1)))
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
            .assertAny(hasTextExactly("${NEW_1}3${NEW_2}6$NEW_3"))
        composeTestRule.assertGameScreenDisabled()

        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .performClick()

        composeTestRule.waitForIdle()

        // flip first X1
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()[2].performClick()

        composeTestRule.waitForIdle()

        // nothing should change except flipped tile
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filterToOne(hasContentDescriptionExactly("${FLIPPED_DESCR}1"))
        composeTestRule.onNodeWithTag(SPEECH_TAG)
            .assertDoesNotExist()
        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .assertDoesNotExist()

        // flip another x2
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()[8].performClick()

        composeTestRule.waitForIdle()

        // speech box should appear again
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filter(hasContentDescriptionExactly("${FLIPPED_DESCR}2"))
            .assertCountEquals(2)
        composeTestRule.onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .onChildren()
            .assertAny(hasTextExactly("${NEW_1}2${NEW_2}12$NEW_3"))
        composeTestRule.assertGameScreenDisabled()
        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .assertIsEnabled()
    }

    @Test
    fun `VoltorbFlipApp game loss follows expected sequence`() {
        composeTestRule.setContent {
            VoltorbFlipApp(VoltorbFlipAppState(GameGrid(testerLv1)))
        }

        composeTestRule.performClickToPlay()

        composeTestRule.waitForIdle()

        // flip a x2
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()[0].performClick()
        composeTestRule.waitForIdle()
        // click away pause speech box
        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .performClick()
        composeTestRule.waitForIdle()

        // coins should have increased
        composeTestRule.onAllNodesWithTag(COIN_TAG)
            .filterToOne(hasAnyChild(hasTextExactly(CURRENT_COINS, "00002")))

        // flip a zero
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()[1].performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filterToOne(hasContentDescriptionExactly("${FLIPPED_DESCR}0"))
        // game loss screen should start over disabled grid
        composeTestRule.assertGameScreenDisabled()
        composeTestRule.onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .onChild()
            .assertTextEquals(NO_COINS)
        // coins should be decremented
        composeTestRule.onAllNodesWithTag(COIN_TAG)
            .filterToOne(hasAnyChild(hasTextExactly(CURRENT_COINS, "00000")))

        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .performClick()
        composeTestRule.waitForIdle()

        // all tiles should be flipped
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filter(hasContentDescriptionExactly("${FLIPPED_DESCR}0"))
            .assertCountEquals(6)
        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .performClick()

        composeTestRule.waitForIdle()

        // grid should be cleared of data
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filter(hasContentDescriptionExactly(INFO_ZERO_DESCR))
            .assertCountEquals(10)
            .assertAll(hasTextExactly("00", "0"))
        // level cannot drop below 1 so speech box is in pre-game
        composeTestRule.onNodeWithText("${HEADER_START}1$HEADER_END")
            .assertExists()
        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(3)
    }

    @Test
    fun `VoltorbFlipApp game level drops after loss`() {
        val state = VoltorbFlipAppState(GameGrid(testerLv5)).apply { currentLevel = 5 }
        composeTestRule.setContent {
            VoltorbFlipApp(state)
        }

        composeTestRule.performClickToPlay()

        composeTestRule.waitForIdle()

        // flip a x2
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()[0].performClick()
        composeTestRule.waitForIdle()
        // click away pause speech box
        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .performClick()
        composeTestRule.waitForIdle()

        // coins should have increased
        composeTestRule.onAllNodesWithTag(COIN_TAG)
            .filterToOne(hasAnyChild(hasTextExactly(CURRENT_COINS, "00002")))

        // flip a zero
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()[12].performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filterToOne(hasContentDescriptionExactly("${FLIPPED_DESCR}0"))
        // game loss screen should start over disabled grid
        composeTestRule.assertGameScreenDisabled()
        composeTestRule.onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .onChild()
            .assertTextEquals(NO_COINS)
        composeTestRule.onAllNodesWithTag(COIN_TAG)
            .filterToOne(hasAnyChild(hasTextExactly(CURRENT_COINS, "00000")))

        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .performClick()
        composeTestRule.waitForIdle()

        // all tiles should be flipped
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filter(hasContentDescriptionExactly("${FLIPPED_DESCR}0"))
            .assertCountEquals(10)
        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .performClick()

        composeTestRule.waitForIdle()

        // grid should be cleared of data & level dropped to num of flipped tiles
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filter(hasContentDescriptionExactly(INFO_ZERO_DESCR))
            .assertCountEquals(10)
            .assertAll(hasTextExactly("00", "0"))
        composeTestRule.onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .onChild()
            .assertTextEquals("${DROPPED_START}2$DROPPED_END")
        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .performClick()

        composeTestRule.waitForIdle()

        // should be in pre-game now
        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(3)
    }
}