package components.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.components.screens.HintScreen
import dev.bogwalk.ui.style.HINT_INFO
import dev.bogwalk.ui.style.INFO_ARROW_DESCR
import dev.bogwalk.ui.style.TILE_TAG
import org.junit.Rule
import kotlin.test.Test

class HintScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `HintScreen loads with correct disabled content`() {
        composeTestRule.setContent {
            HintScreen()
        }

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .assertCountEquals(12)
            .assertAll(isNotEnabled())

        composeTestRule.onNodeWithContentDescription(INFO_ARROW_DESCR)
            .assertExists()

        composeTestRule.onNodeWithText(HINT_INFO)
            .assertExists()
    }
}