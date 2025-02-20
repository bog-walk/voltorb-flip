package components.tiles

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.model.GameTile
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.components.tiles.TileGrid
import dev.bogwalk.ui.style.*
import org.junit.Rule
import kotlin.test.Test

class TileGridTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `TileGrid loads with correct content`() {
        val grid = mutableStateOf(List(5) { r ->
            List(5) { c -> GameTile(r to c, 1) }
        }.flatten())

        composeTestRule.setContent {
            TileGrid(
                screen = Screen.IN_GAME,
                grid = grid.value,
                infoGrid = listOf(
                    7 to 0, 6 to 0, 6 to 1, 3 to 2, 2 to 3, 5 to 1, 6 to 1, 2 to 3, 6 to 0, 5 to 1
                ),
                currentPosition = 0 to 0,
                isMemoOpen = false) {}
        }

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .assertCountEquals(35)
            .filter(isNotEnabled())
            .assertCountEquals(10)
            .assertAll(hasContentDescriptionExactly(INFO_ZERO_DESCR))

        grid.value = List(5) { r -> List(5) { c ->
            if (r == 1) {
                GameTile(1 to c, 1, isFlipped = true)
            } else {
                GameTile(r to c, 1)
            }
        }}.flatten()
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .assertCountEquals(35)
            .filter(isNotEnabled())
            .assertCountEquals(15)
            .filter(hasContentDescriptionExactly("${FLIPPED_DESCR}1"))
            .assertCountEquals(5)
    }

    @Test
    fun `TileGrid has maximum 1 pencil when memo open`() {
        val position = mutableStateOf(0 to 0)
        val padOpen = mutableStateOf(false)

        composeTestRule.setContent {
            TileGrid(
                screen = Screen.IN_GAME,
                grid = List(5) { r -> List(5) { c ->
                    when (r) {
                        0 -> GameTile(0 to c, 1,
                            memoData = listOf(true, false, false, false))
                        1 -> GameTile(1 to c, 1, isFlipped = true)
                        else -> GameTile(r to c, 1)
                    }
                } }.flatten(),
                infoGrid = listOf(
                    5 to 0, 5 to 0, 6 to 2, 3 to 2, 2 to 3, 5 to 2, 6 to 2, 2 to 3, 6 to 0, 5 to 2
                ),
                currentPosition = position.value,
                isMemoOpen = padOpen.value) {}
        }

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .assertCountEquals(35)
            .filter(hasContentDescriptionExactly(MEMO_PENCIL_DESCR))
            .assertCountEquals(0)

        padOpen.value = true
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(TILE_TAG, useUnmergedTree = true)
            .filter(hasAnyChild(hasTestTag(ZERO_TAG)))
            .assertCountEquals(5)
            .filterToOne(hasContentDescriptionExactly(MEMO_PENCIL_DESCR))

        position.value = 1 to 0
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .filter(hasContentDescription("${FLIPPED_DESCR}1"))
            .assertCountEquals(5)
            .filterToOne(hasContentDescriptionExactly(MEMO_PENCIL_DESCR))

        position.value = -1 to -1  // quit button in focus
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .filter(hasContentDescriptionExactly(MEMO_PENCIL_DESCR))
            .assertCountEquals(0)
    }
}