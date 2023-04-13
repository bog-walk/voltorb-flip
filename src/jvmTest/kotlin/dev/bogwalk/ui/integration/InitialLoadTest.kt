package dev.bogwalk.ui.integration

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.VoltorbFlipApp
import dev.bogwalk.ui.assertGameScreenDisabled
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.VoltorbFlipAppState
import org.junit.Rule
import kotlin.test.Test

class InitialLoadTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `VoltorbFlipApp loads initial components correctly`() {
        composeTestRule.setContent {
            VoltorbFlipApp(VoltorbFlipAppState())
        }

        // top screen
        composeTestRule.onNodeWithText("${HEADER_START}1$HEADER_END")
            .assertExists()
        composeTestRule.onAllNodesWithTag(COIN_TAG)
            .assertCountEquals(2)
            .assertAll(hasAnyChild(hasTextExactly("00000")))

        // game screen (no game date & all disabled)
        composeTestRule.assertGameScreenDisabled()
        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filter(hasContentDescriptionExactly(INFO_ZERO_DESCR))
            .assertCountEquals(10)
            .assertAll(hasTextExactly("00", "0"))

        // overlay screen
        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(3)
        composeTestRule.onNodeWithTag(SPEECH_TAG)
            .onChild()
            .assertTextEquals(START_GAME)
    }
}