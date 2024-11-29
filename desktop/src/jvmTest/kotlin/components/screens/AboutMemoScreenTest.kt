package components.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.components.screens.AboutMemoScreen
import dev.bogwalk.ui.style.*
import org.junit.Rule
import kotlin.test.Test

class AboutMemoScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `AboutMemoScreen loads with correct disabled content`() {
        composeTestRule.setContent {
            AboutMemoScreen()
        }

        composeTestRule.onNodeWithTag(MEMO_TAG)
            .assertIsNotEnabled()

        composeTestRule.onAllNodesWithContentDescription(INFO_STYLUS_DESCR)
            .assertCountEquals(2)

        composeTestRule.onAllNodesWithContentDescription(INFO_ARROW_DESCR)
            .assertCountEquals(2)

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(5)
            .assertAll(isNotEnabled())

        composeTestRule.onNodeWithTag(TILE_TAG)
            .assertIsNotEnabled()

        composeTestRule.onNodeWithText(ABOUT_INFO)
            .assertExists()
    }
}