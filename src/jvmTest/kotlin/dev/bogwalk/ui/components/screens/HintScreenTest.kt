package dev.bogwalk.ui.components.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
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

        composeTestRule.onAllNodesWithContentDescription(INFO_ARROW_DESCR)
            .assertCountEquals(1)
    }
}