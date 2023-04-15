package dev.bogwalk.ui.components.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.onSpeechBox
import dev.bogwalk.ui.style.*
import org.junit.Rule
import kotlin.test.Test

class OverlayScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `OverlayScreen is only enabled in 1 screen state`() {
        val screen = mutableStateOf(Screen.REVEAL)

        composeTestRule.setContent {
            OverlayScreen(screen.value, 1, 0, {}, {}, {}, {}, {}, {}, {})
        }

        composeTestRule.onNodeWithTag(OVERLAY_TAG)
            .assertExists()
            .assertIsEnabled()

        for (screenType in Screen.values()) {
            if (screenType == Screen.REVEAL) continue

            screen.value = screenType
            composeTestRule.waitForIdle()

            composeTestRule.onNodeWithTag(OVERLAY_TAG)
                .assertIsNotEnabled()
        }
    }

    @Test
    fun `OverlayScreen changes content based on screen state`() {
        val state = mutableStateOf(Screen.PRE_GAME)

        composeTestRule.setContent {
            OverlayScreen(state.value, 1, 0, {}, {}, {}, {}, {}, {}, {})
        }

        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(3)
        composeTestRule.onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .onChild()
            .assertTextEquals("${START_GAME_START}1$START_GAME_END")

        state.value = Screen.PRE_INFO
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(4)
        composeTestRule.onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .onChild()
            .assertTextEquals(REQUEST_INFO)

        state.value = Screen.QUITTING
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(YES)
            .assertIsEnabled()
        composeTestRule.onNodeWithText(NO)
            .assertIsEnabled()
        composeTestRule.onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .onChild()
            .assertTextEquals(QUIT_NO_COINS)

        state.value = Screen.ABOUT_GAME
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(OPTIONS_TAG)
            .assertDoesNotExist()
        composeTestRule.onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .onChildren()
            .assertCountEquals(2)
            .filterToOne(hasAnyChild(hasContentDescriptionExactly(NEXT_ARROW_DESCR)))
            .assertIsEnabled()
    }

    @Test
    fun `OverlayScreen changes content when quitting with coins`() {
        val coins = 12
        composeTestRule.setContent {
            OverlayScreen(Screen.QUITTING, 1, coins, {}, {}, {}, {}, {}, {}, {})
        }

        composeTestRule.onNodeWithText(YES)
            .assertDoesNotExist()
        composeTestRule.onNodeWithText(NO)
            .assertDoesNotExist()

        composeTestRule.onSpeechBox(
            "$QUIT_START$coins${QUIT_END.substringBefore("\n")}")
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(YES)
            .assertIsEnabled()
        composeTestRule.onNodeWithText(NO)
            .assertIsEnabled()
        // click next arrow even though at end of text
        composeTestRule.onSpeechBox(
            "${QUIT_START.substringAfter("\n")}$coins${QUIT_END}")
            .performClick()

        composeTestRule.waitForIdle()

        // there should be no change
        composeTestRule.onNodeWithText(YES)
            .assertIsEnabled()
        composeTestRule.onNodeWithText(NO)
            .assertIsEnabled()
        composeTestRule.onSpeechBox(
            "${QUIT_START.substringAfter("\n")}$coins${QUIT_END}")
    }
}