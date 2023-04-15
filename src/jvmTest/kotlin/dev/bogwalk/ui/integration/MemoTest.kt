package dev.bogwalk.ui.integration

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.VoltorbFlipApp
import dev.bogwalk.ui.assertGameScreenEnabled
import dev.bogwalk.ui.performClickToPlay
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.VoltorbFlipAppState
import org.junit.Rule
import kotlin.test.Test

class MemoTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `VoltorbFlipApp closes memo through pad arrow`() {
        composeTestRule.setContent {
            VoltorbFlipApp(VoltorbFlipAppState())
        }

        composeTestRule.performClickToPlay()

        composeTestRule.waitForIdle()

        composeTestRule.assertGameScreenEnabled()
        // open memo
        composeTestRule.onNodeWithTag(MEMO_TAG)
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(MEMO_TAG)
            .assertTextEquals(CLOSE)
        // click memo pad arrow
        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(5)
            .filterToOne(hasContentDescriptionExactly(MEMO_ARROW_DESCR))
            .performClick()

        composeTestRule.waitForIdle()

        // should be closed
        composeTestRule.onNodeWithTag(MEMO_TAG)
            .assertTextEquals(OPEN)
        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(0)
    }
}