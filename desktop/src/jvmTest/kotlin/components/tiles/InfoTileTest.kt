package components.tiles

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.components.tiles.InfoTile
import dev.bogwalk.ui.style.INFO_ZERO_DESCR
import dev.bogwalk.ui.style.TILE_TAG
import org.junit.Rule
import kotlin.test.Test

class InfoTileTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `InfoTile is not enabled in game & loads with correct content`() {
        composeTestRule.setContent {
            InfoTile(0, 4, 1)
        }

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .assertExists()
            .assertIsNotEnabled()
            .onChildren()
            .assertCountEquals(3)
            .assertAny(hasContentDescriptionExactly(INFO_ZERO_DESCR))
            .assertAny(hasTextExactly("04"))
            .assertAny(hasTextExactly("1"))
    }
}