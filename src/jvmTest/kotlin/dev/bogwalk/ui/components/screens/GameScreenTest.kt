package dev.bogwalk.ui.components.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.model.GameTile
import dev.bogwalk.ui.style.*
import org.junit.Rule
import kotlin.test.Test

class GameScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `GameScreen loads with initial correct content`() {
        composeTestRule.setContent {
            GameScreen(
                List(5) { r -> List(5) { c -> GameTile(r to c, 1) } },
                listOf(7 to 0, 6 to 0, 6 to 1, 3 to 2, 2 to 3, 5 to 1, 6 to 1, 2 to 3, 6 to 0, 5 to 1),
                0 to 0, false,
                {}, {}, {}) {}
        }

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .assertCountEquals(35)

        composeTestRule.onNodeWithTag(MEMO_TAG)
            .assertExists()
            .assertIsEnabled()

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(0)

        composeTestRule.onNodeWithText(QUIT)
            .assertExists()
            .assertIsEnabled()
    }

    @Test
    fun `GameScreen changes content when memo is open`() {
        val padOpen = mutableStateOf(false)

        composeTestRule.setContent {
            GameScreen(
                List(5) { r -> List(5) { c -> GameTile(r to c, 1) } },
                listOf(7 to 0, 6 to 0, 6 to 1, 3 to 2, 2 to 3, 5 to 1, 6 to 1, 2 to 3, 6 to 0, 5 to 1),
                0 to 0,
                isMemoOpen = padOpen.value,
                {}, {}, {}) {}
        }

        composeTestRule.onNodeWithTag(MEMO_TAG)
            .assertTextEquals(OPEN)

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(0)

        padOpen.value = true
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(MEMO_TAG)
            .assertTextEquals(CLOSE)

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(5)
            .assertAll(isEnabled())

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .filterToOne(hasContentDescriptionExactly(PIXEL_PENCIL_DESCR))
    }

    @Test
    fun `GameScreen disables most of memo pad if quit button in focus`() {
        val position = mutableStateOf(0 to 0)

        composeTestRule.setContent {
            GameScreen(
                List(5) { r -> List(5) { c -> GameTile(r to c, 1) } },
                listOf(7 to 0, 6 to 0, 6 to 1, 3 to 2, 2 to 3, 5 to 1, 6 to 1, 2 to 3, 6 to 0, 5 to 1),
                currentPosition = position.value,
                isMemoOpen = true,
                {}, {}, {}) {}
        }

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(5)
            .assertAll(isEnabled())

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .filterToOne(hasContentDescriptionExactly(PIXEL_PENCIL_DESCR))

        position.value = -1 to -1
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .filterToOne(isEnabled())
            .assertContentDescriptionEquals(PIXEL_ARROW_DESCR)

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .filter(hasContentDescriptionExactly(PIXEL_PENCIL_DESCR))
            .assertCountEquals(0)
    }
}