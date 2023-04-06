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
    fun `InfoScreen loads with correct initial content`() {
        composeTestRule.setContent {
            InfoScreen(1, 0, 0)
        }

        composeTestRule.onNodeWithText("${HEADER_START}1$HEADER_END")
            .assertExists()

        composeTestRule.onAllNodesWithTag(TILE_TAG)
            .assertCountEquals(4)
            .assertAll(isNotEnabled())

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
        val total = mutableStateOf(999)
        val current = mutableStateOf(0)

        composeTestRule.setContent {
            InfoScreen(1, total.value, current.value)
        }

        composeTestRule.onAllNodesWithTag(COIN_TAG)
            .filterToOne(
                hasAnyChild(hasTextExactly(PLAYER_COINS)) and hasAnyChild(hasTextExactly("00999"))
            )

        composeTestRule.onAllNodesWithTag(COIN_TAG)
            .filterToOne(
                hasAnyChild(hasTextExactly(CURRENT_COINS)) and hasAnyChild(hasTextExactly("00001"))
            )

        current.value = 1
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(COIN_TAG)
            .filterToOne(
                hasAnyChild(hasTextExactly(CURRENT_COINS)) and hasAnyChild(hasTextExactly("00001"))
            )

        total.value = 1000
        current.value = 0
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(COIN_TAG)
            .filterToOne(
                hasAnyChild(hasTextExactly(PLAYER_COINS)) and hasAnyChild(hasTextExactly("01000"))
            )

        composeTestRule.onAllNodesWithTag(COIN_TAG)
            .filterToOne(
                hasAnyChild(hasTextExactly(CURRENT_COINS)) and hasAnyChild(hasTextExactly("00000"))
            )
    }
}