package dev.bogwalk.ui.integration

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.VoltorbFlipApp
import dev.bogwalk.ui.assertGameScreenDisabled
import dev.bogwalk.ui.onSpeechBox
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.VoltorbFlipAppState
import org.junit.Rule
import kotlin.test.Test

class NavigateScreensTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `VoltorbFlipApp loads requested info screen with disabled GameScreen`() {
        composeTestRule.setContent {
            VoltorbFlipApp(VoltorbFlipAppState())
        }

        // top screen initially shows info screen
        composeTestRule.onNodeWithText("${HEADER_START}1$HEADER_END")
            .assertExists()

        // request more info
        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .filterToOne(hasTextExactly(INFO))
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.assertGameScreenDisabled()
        // choose how to play
        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .filterToOne(hasTextExactly(HINT))
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.assertGameScreenDisabled()
        composeTestRule.onAllNodesWithContentDescription(INFO_ARROW_DESCR)
            .assertCountEquals(1)

        val expected = HINT_TEXT.split("|").map { it.split("\n") }

        composeTestRule.onSpeechBox(expected[0].take(2).joinToString("\n"))
            .performClick()

        composeTestRule.waitForIdle()

        repeat(6) {
            composeTestRule.onSpeechBox()
                .performClick()

            composeTestRule.waitForIdle()
        }

        composeTestRule.onSpeechBox(expected[3].takeLast(2).joinToString("\n"))
            .performClick()

        composeTestRule.waitForIdle()

        // choose to no longer see info
        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .filterToOne(hasTextExactly(RETURN))
            .performClick()

        composeTestRule.waitForIdle()

        // should be brought back to pre-game screen
        composeTestRule.assertGameScreenDisabled()
        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(3)
        composeTestRule.onNodeWithText("${HEADER_START}1$HEADER_END")
            .assertExists()
    }

    @Test
    fun `VoltorbFlipApp keeps info screen when not requesting more info`() {
        composeTestRule.setContent {
            VoltorbFlipApp(VoltorbFlipAppState())
        }

        // top screen initially shows info screen
        composeTestRule.onNodeWithText("${HEADER_START}1$HEADER_END")
            .assertExists()
        composeTestRule.assertGameScreenDisabled()

        // request more info
        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .filterToOne(hasTextExactly(INFO))
            .performClick()

        composeTestRule.waitForIdle()

        // choose to return
        composeTestRule.assertGameScreenDisabled()
        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .filterToOne(hasTextExactly(RETURN))
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("${HEADER_START}1$HEADER_END")
            .assertExists()
        composeTestRule.assertGameScreenDisabled()
        // choose to play
        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .filterToOne(hasTextExactly(PLAY))
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("${HEADER_START}1$HEADER_END")
            .assertExists()
        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .filter(isEnabled())
            .assertCountEquals(25)
        composeTestRule.onNodeWithTag(MEMO_TAG)
            .assertIsEnabled()
        composeTestRule.onNodeWithText(QUIT)
            .assertIsEnabled()
    }
}