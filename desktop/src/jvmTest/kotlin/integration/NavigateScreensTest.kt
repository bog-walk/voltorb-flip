package integration

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import assertGameScreenDisabled
import dev.bogwalk.ui.VoltorbFlipApp
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.VoltorbFlipAppState
import onSpeechBox
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
        composeTestRule.assertGameScreenDisabled()

        // request more info
        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .filterToOne(hasTextExactly(INFO))
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("${HEADER_START}1$HEADER_END")
            .assertExists()
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
            .assertCountEquals(4)
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
}