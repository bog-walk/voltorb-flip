package components.tiles

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.components.tiles.Tile
import dev.bogwalk.ui.components.tiles.TileState
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
    fun `Tile is only enabled in 1 screen state`() {
        val screen = mutableStateOf(Screen.IN_GAME)

        composeTestRule.setContent {
            Tile(screen.value, 0 to 0, TileState.NOT_FLIPPED, onSelectRequest = {}) {}
        }

        composeTestRule.onNodeWithTag(TILE_TAG)
            .assertExists()
            .assertIsEnabled()

        for (screenType in Screen.entries) {
            if (screenType == Screen.IN_GAME) continue

            screen.value = screenType
            composeTestRule.waitForIdle()

            composeTestRule.onNodeWithTag(TILE_TAG)
                .assertIsNotEnabled()
        }
    }
}