package dev.bogwalk.ui.integration

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.model.GameGrid
import dev.bogwalk.model.TestLevelGenerator
import dev.bogwalk.ui.VoltorbFlipApp
import dev.bogwalk.ui.assertGameScreenDisabled
import dev.bogwalk.ui.assertGameScreenEnabled
import dev.bogwalk.ui.performClickToPlay
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
        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .assertIsNotEnabled()
        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(3)
        composeTestRule.onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .onChildren()
            .assertAny(hasTextExactly(START_GAME))
    }

    @Test
    fun `VoltorbFlipApp loads first round of new game correctly`() {
        val tester = TestLevelGenerator().apply {
            toAssign = listOf(0, 7, 9, 24, 1, 4, 12, 14, 15, 21)
        }

        composeTestRule.setContent {
            VoltorbFlipApp(VoltorbFlipAppState(GameGrid(tester)))
        }

        composeTestRule.performClickToPlay()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .assertDoesNotExist()
        composeTestRule.assertGameScreenEnabled()

        composeTestRule.onNodeWithTag(GRID_TAG)
            .onChildren()
            .filter(isNotEnabled())
            .assertCountEquals(10)
            .assertAny(hasTextExactly("04", "2"))
            .assertAny(hasTextExactly("07", "0"))
            .assertAny(hasTextExactly("03", "2"))
            .assertAny(hasTextExactly("04", "1"))
            .assertAny(hasTextExactly("06", "1"))
            .assertAny(hasTextExactly("05", "1"))
            .assertAny(hasTextExactly("03", "2"))
            .assertAny(hasTextExactly("05", "1"))
            .assertAny(hasTextExactly("05", "0"))
            .assertAny(hasTextExactly("06", "2"))
    }
}