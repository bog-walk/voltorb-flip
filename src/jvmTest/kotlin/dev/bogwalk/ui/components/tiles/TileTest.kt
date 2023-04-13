package dev.bogwalk.ui.components.tiles

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.style.TILE_TAG
import org.junit.Rule
import kotlin.test.Test

class TileTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `Tile is enabled in only 1 tile state`() {
        val state = mutableStateOf(TileState.STATIC_INFO)

        composeTestRule.setContent {
            Tile(Screen.IN_GAME, 0 to 0, state.value, onSelectRequest = {}) {}
        }

        composeTestRule.onNodeWithTag(TILE_TAG)
            .assertExists()
            .assertIsNotEnabled()

        state.value = TileState.NOT_FLIPPED
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TILE_TAG)
            .assertIsEnabled()

        state.value = TileState.FLIPPED
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TILE_TAG)
            .assertIsNotEnabled()

    }

    @Test
    fun `Tile is only enabled when positioned in grid and in game`() {
        val position = mutableStateOf(0 to 0)
        val screen = mutableStateOf(Screen.IN_GAME)

        composeTestRule.setContent {
            Tile(screen.value, position.value, TileState.NOT_FLIPPED, onSelectRequest = {}) {}
        }

        composeTestRule.onNodeWithTag(TILE_TAG)
            .assertExists()
            .assertIsEnabled()

        position.value = -1 to -1  // info tile with no connectors
        screen.value = Screen.ABOUT_GAME
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TILE_TAG)
            .assertIsNotEnabled()

        position.value = 3 to -1  // info tile with row connectors
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TILE_TAG)
            .assertIsNotEnabled()

    }
}