package dev.bogwalk.ui.components.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.onSpeechBox
import dev.bogwalk.ui.style.*
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals

class OverlayScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `OverlayScreen is only enabled in 1 screen state`() {
        val screen = mutableStateOf(Screen.REVEAL)

        composeTestRule.setContent {
            OverlayScreen(screen.value, {}, {}, {}, {}, {}) {}
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
            OverlayScreen(state.value, {}, {}, {}, {}, {}) {}
        }

        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(3)
        composeTestRule.onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .onChild()
            .assertTextEquals(START_GAME)

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
            .assertExists()
            .assertIsEnabled()
        composeTestRule.onNodeWithText(NO)
            .assertExists()
            .assertIsEnabled()
        composeTestRule.onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .onChild()
            .assertTextEquals(QUIT_GAME)

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
    fun `SpeechBox iterates through text when clicked`() {
        val expected = HOW_TO_TEXT.split("|").map { it.split("\n") }

        composeTestRule.setContent {
            OverlayScreen(Screen.ABOUT_GAME, {}, {}, {}, {}, {}) {}
        }

        composeTestRule.onSpeechBox(expected[0].take(2).joinToString("\n"))
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onSpeechBox(expected[0].takeLast(2).joinToString("\n"))
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onSpeechBox(expected[1].take(2).joinToString("\n"))
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onSpeechBox(expected[2].take(2).joinToString("\n"))
            .performClick()
    }

    @Test
    fun `SpeechBox correctly calls function when text is finished`() {
        val expected = ABOUT_TEXT.split("|").map { it.split("\n") }
        var screen = Screen.ABOUT_MEMO

        composeTestRule.setContent {
            OverlayScreen(screen, {}, {}, {}, {}, {}) { screen = Screen.values()[it] }
        }

        composeTestRule.onSpeechBox(expected[0].take(2).joinToString("\n"))
            .performClick()

        composeTestRule.waitForIdle()

        repeat(5) {
            composeTestRule.onSpeechBox()
                .performClick()

            composeTestRule.waitForIdle()
        }

        composeTestRule.onSpeechBox(expected[3].take(2).joinToString("\n"))
            .performClick()

        composeTestRule.waitForIdle()

        assertEquals(Screen.PRE_INFO, screen)
    }
}