package dev.bogwalk.ui.components.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.style.*
import org.junit.Rule
import kotlin.test.Test

class InfoScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `InfoScreen loads with correct disabled content`() {
        composeTestRule.setContent {
            InfoScreen(1, 0, 0)
        }

        composeTestRule.onNodeWithText("${HEADER_START}1$HEADER_END")
            .assertExists()

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .assertCountEquals(4)
            .assertAll(isNotEnabled())

        composeTestRule.onNodeWithText(INFO_POINTS)
            .assertExists()

        composeTestRule.onNodeWithText(GAME_OVER)
            .assertExists()

        composeTestRule.onAllNodesWithTag(COIN_TAG)
            .filterToOne(
                hasAnyChild(hasTextExactly(PLAYER_COINS)) and hasAnyChild(hasTextExactly("00000"))
            )

        composeTestRule.onAllNodesWithTag(COIN_TAG)
            .filterToOne(
                hasAnyChild(hasTextExactly(CURRENT_COINS)) and hasAnyChild(hasTextExactly("00000"))
            )
    }

    @Test
    fun `InfoScreen adjusts coins accordingly`() {
        val current = mutableStateOf(0)

        composeTestRule.setContent {
            InfoScreen(1, 0, current.value)
        }

        composeTestRule.onAllNodesWithTag(COIN_TAG)
            .filterToOne(
                hasAnyChild(hasTextExactly(CURRENT_COINS)) and hasAnyChild(hasTextExactly("00001"))
            )

        current.value = 99
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(COIN_TAG)
            .filterToOne(
                hasAnyChild(hasTextExactly(CURRENT_COINS)) and hasAnyChild(hasTextExactly("00099"))
            )
    }
}