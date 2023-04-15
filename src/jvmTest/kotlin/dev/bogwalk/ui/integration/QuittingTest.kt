package dev.bogwalk.ui.integration

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.model.GameGrid
import dev.bogwalk.model.TestLevelGenerator
import dev.bogwalk.ui.VoltorbFlipApp
import dev.bogwalk.ui.performClickToPlay
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.VoltorbFlipAppState
import org.junit.Rule
import kotlin.test.Test

class QuittingTest {
    companion object {
        private val tester = TestLevelGenerator().apply {
            toAssign = listOf(0, 7, 9, 24, 1, 4, 12, 14, 15, 21)
        }
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `VoltorbFlipApp goes to exit screen when quit from pre-game`() {
        composeTestRule.setContent {
            VoltorbFlipApp(VoltorbFlipAppState())
        }

        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .filterToOne(hasTextExactly(QUIT))
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(THANKS)
            .assertExists()
        composeTestRule.onNodeWithContentDescription("$FLIPPED_DESCR$0")
            .assertExists()
    }

    @Test
    fun `VoltorbFlipApp allows abort of quit in game`() {
        composeTestRule.setContent {
            VoltorbFlipApp(VoltorbFlipAppState(GameGrid(tester)))
        }

        composeTestRule.performClickToPlay()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filterToOne(isNotEnabled() and hasTextExactly("04", "2"))

        // start to quit
        composeTestRule.onNodeWithText(QUIT)
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .onChild()
            .assertTextEquals(QUIT_NO_COINS)
        // abort quit
        composeTestRule.onNodeWithText(NO)
            .performClick()

        composeTestRule.waitForIdle()

        // grid data should be unchanged
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filterToOne(isNotEnabled() and hasTextExactly("04", "2"))
    }

    @Test
    fun `VoltorbFlipApp reveals all tiles & clears grid when quit in game`() {
        composeTestRule.setContent {
            VoltorbFlipApp(VoltorbFlipAppState(GameGrid(tester)))
        }

        composeTestRule.performClickToPlay()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filterToOne(isNotEnabled() and hasTextExactly("04", "2"))

        // start to quit
        composeTestRule.onNodeWithText(QUIT)
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .onChild()
            .assertTextEquals(QUIT_NO_COINS)
        // actually quit
        composeTestRule.onNodeWithText(YES)
            .performClick()

        composeTestRule.waitForIdle()

        // grid data should be revealed
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .assertAll(isNotEnabled())
            .filter(hasContentDescriptionExactly("${FLIPPED_DESCR}0"))
            .assertCountEquals(6)
        // overlay screen enabled do grid data can be viewed as wished
        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .assertIsEnabled()
            .performClick()

        composeTestRule.waitForIdle()

        // now in pre-game, so grid data should be cleared
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filter(hasContentDescriptionExactly(INFO_ZERO_DESCR))
            .assertCountEquals(10)
            .assertAll(hasTextExactly("00", "0"))
        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(3)
    }

    @Test
    fun `VoltorbFlipApp closes open memo if quit in game`() {
        composeTestRule.setContent {
            VoltorbFlipApp(VoltorbFlipAppState())
        }

        composeTestRule.performClickToPlay()

        composeTestRule.waitForIdle()

        // open memo
        composeTestRule.onNodeWithTag(MEMO_TAG)
            .assertTextEquals(OPEN)
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(MEMO_TAG)
            .assertTextEquals(CLOSE)
        // pick a tile to mark
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filter(isEnabled())
            .onFirst()
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(5)
            .onFirst()
            .performClick()

        composeTestRule.waitForIdle()

        // confirm mark present on same tile with pencil icon
        composeTestRule.onNodeWithTag(GRID_TAG, useUnmergedTree = true)
            .onChildren()
            .filterToOne(isEnabled() and
                        hasContentDescriptionExactly(MEMO_PENCIL_DESCR) and hasTestTag(ZERO_TAG))

        composeTestRule.onNodeWithText(QUIT)
            .performClick()

        composeTestRule.waitForIdle()

        // memo pad should close
        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(0)
        composeTestRule.onNodeWithTag(MEMO_TAG)
            .assertTextEquals(OPEN)
        composeTestRule.onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .onChild()
            .assertTextEquals(QUIT_NO_COINS)
        // abort quit
        composeTestRule.onNodeWithText(NO)
            .performClick()

        composeTestRule.waitForIdle()

        // mark should still be present with no pencil
        composeTestRule.onNodeWithTag(GRID_TAG, useUnmergedTree = true)
            .onChildren()
            .filterToOne(isEnabled() and
                    hasContentDescriptionExactly(MEMO_PENCIL_DESCR) and hasTestTag(ZERO_TAG))
    }
}