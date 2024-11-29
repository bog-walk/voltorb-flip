package components.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import assertGameScreenDisabled
import assertGameScreenEnabled
import dev.bogwalk.model.GameTile
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.components.screens.GameScreen
import dev.bogwalk.ui.style.*
import org.junit.Rule
import kotlin.test.Test

class GameScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `GameScreen loads with correct content`() {
        composeTestRule.setContent {
            GameScreen(
                Screen.IN_GAME,
                List(5) { r -> List(5) { c -> GameTile(r to c, 1) } }.flatten(),
                listOf(7 to 0, 6 to 0, 6 to 1, 3 to 2, 2 to 3, 5 to 1, 6 to 1, 2 to 3, 6 to 0, 5 to 1),
                0 to 0, false,
                {}, {}, {}) {}
        }

        composeTestRule.assertGameScreenEnabled()

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(0)
    }

    @Test
    fun `GameScreen entirely disabled if not in game`() {
        val screen = mutableStateOf(Screen.IN_GAME)

        composeTestRule.setContent {
            GameScreen(
                screen.value,
                List(5) { r -> List(5) { c -> GameTile(r to c, 1) } }.flatten(),
                listOf(7 to 0, 6 to 0, 6 to 1, 3 to 2, 2 to 3, 5 to 1, 6 to 1, 2 to 3, 6 to 0, 5 to 1),
                0 to 0, false,
                {}, {}, {}) {}
        }

        composeTestRule.assertGameScreenEnabled()

        for (screenType in Screen.entries) {
            if (screenType == Screen.IN_GAME) continue

            screen.value = screenType
            composeTestRule.waitForIdle()

            composeTestRule.assertGameScreenDisabled()
        }
    }

    @Test
    fun `GameScreen changes content when memo is open`() {
        val padOpen = mutableStateOf(false)

        composeTestRule.setContent {
            GameScreen(
                Screen.IN_GAME,
                List(5) { r -> List(5) { c -> GameTile(r to c, 1) } }.flatten(),
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
            .filterToOne(hasContentDescriptionExactly(MEMO_PENCIL_DESCR))
    }

    @Test
    fun `GameScreen disables most of memo pad if quit button in focus`() {
        val position = mutableStateOf(0 to 0)

        composeTestRule.setContent {
            GameScreen(
                Screen.IN_GAME,
                List(5) { r -> List(5) { c -> GameTile(r to c, 1) } }.flatten(),
                listOf(7 to 0, 6 to 0, 6 to 1, 3 to 2, 2 to 3, 5 to 1, 6 to 1, 2 to 3, 6 to 0, 5 to 1),
                currentPosition = position.value,
                isMemoOpen = true,
                {}, {}, {}) {}
        }

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(5)
            .assertAll(isEnabled())

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .filterToOne(hasContentDescriptionExactly(MEMO_PENCIL_DESCR))

        position.value = -1 to -1
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .filterToOne(isEnabled())
            .assertContentDescriptionEquals(MEMO_ARROW_DESCR)

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .filter(hasContentDescriptionExactly(MEMO_PENCIL_DESCR))
            .assertCountEquals(0)
    }

    @Test
    fun `GameScreen disables most of memo pad if flipped tile in focus`() {
        val position = mutableStateOf(3 to 3)

        composeTestRule.setContent {
            GameScreen(
                Screen.IN_GAME,
                List(5) { r -> List(5) { c ->
                    if (r == 0) GameTile(0 to c, 1, isFlipped = true)
                    else GameTile(r to c, 1)
                } }.flatten(),
                listOf(7 to 0, 6 to 0, 6 to 1, 3 to 2, 2 to 3, 5 to 1, 6 to 1, 2 to 3, 6 to 0, 5 to 1),
                currentPosition = position.value,
                isMemoOpen = true,
                {}, {}, {}) {}
        }

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(5)
            .assertAll(isEnabled())

        position.value = 0 to 0
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .filterToOne(isEnabled())
            .assertContentDescriptionEquals(MEMO_ARROW_DESCR)
    }
}