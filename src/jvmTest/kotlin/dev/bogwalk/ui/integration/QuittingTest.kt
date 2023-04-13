package dev.bogwalk.ui.integration

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.VoltorbFlipApp
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.VoltorbFlipAppState
import org.junit.Rule
import kotlin.test.Test

class QuittingTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `VoltorbFlipApp confirms quit request in game & flips all tiles`() {
        composeTestRule.setContent {
            VoltorbFlipApp(VoltorbFlipAppState())
        }

        // start playing
        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .filterToOne(hasTextExactly(PLAY))
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .filter(isEnabled())
            .assertCountEquals(25)
        // quit without winning any coins
        composeTestRule.onNodeWithText(QUIT)
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(SPEECH_TAG)
            .assertTextEquals(QUIT_GAME)
        // abort quit
        composeTestRule.onNodeWithText(NO)
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .filter(isEnabled())
            .assertCountEquals(25)
        // actually quit
        composeTestRule.onNodeWithText(QUIT)
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(YES)
            .performClick()

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .filter(isEnabled())
            .assertCountEquals(0)
    }
}