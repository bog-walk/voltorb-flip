package dev.bogwalk.ui.components.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.style.INFO_ARROW_DESCR
import dev.bogwalk.ui.style.QUIT
import dev.bogwalk.ui.style.TILE_TAG
import org.junit.Rule
import kotlin.test.Test

class HowToPlayScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `HowToPlayScreen loads with correct disabled content`() {
        composeTestRule.setContent {
            HowToPlayScreen()
        }

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .assertCountEquals(6)
            .assertAll(isNotEnabled())

        composeTestRule.onAllNodesWithContentDescription(INFO_ARROW_DESCR)
            .assertCountEquals(5)

        composeTestRule.onNodeWithText(QUIT)
            .assertExists()
            .assertIsNotEnabled()
    }
}