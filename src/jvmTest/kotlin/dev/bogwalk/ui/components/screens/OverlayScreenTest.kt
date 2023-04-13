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
    fun `OverlayScreen changes content based on screen state`() {
        val state = mutableStateOf(Screen.PRE_GAME)

        composeTestRule.setContent {
            OverlayScreen(state.value, {}, {}, {}, {})
        }

        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(3)
        composeTestRule.onNodeWithTag(SPEECH_TAG)
            .assertExists()

        state.value = Screen.PRE_INFO
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(4)
        composeTestRule.onNodeWithTag(SPEECH_TAG)
            .assertExists()

        state.value = Screen.QUITTING
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(YES)
            .assertExists()
        composeTestRule.onNodeWithText(NO)
            .assertExists()
        composeTestRule.onNodeWithTag(SPEECH_TAG)
            .assertExists()

        state.value = Screen.ABOUT_GAME
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(SPEECH_TAG)
            .assertExists()
    }
    @Test
    fun `SpeechBox changes based on screen`() {
        val state = mutableStateOf(Screen.PRE_GAME)

        composeTestRule.setContent {
            OverlayScreen(state.value, {}, {}, {}, {})
        }

        composeTestRule.onNodeWithTag(SPEECH_TAG)
            .onChild()
            .assertTextEquals(START_GAME)

        state.value = Screen.PRE_INFO
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(SPEECH_TAG)
            .onChild()
            .assertTextEquals(REQUEST_INFO)

        state.value = Screen.QUITTING
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(SPEECH_TAG)
            .onChild()
            .assertTextEquals(QUIT_GAME)

        state.value = Screen.ABOUT_MEMO
        composeTestRule.waitForIdle()

        val expected = ABOUT_TEXT.substringBefore("|")

        composeTestRule.onSpeechBox(expected)
            .assertIsEnabled()
    }

    @Test
    fun `SpeechBox iterates through text when clicked`() {
        val expected = HOW_TO_TEXT.split("|").map { it.split("\n") }

        composeTestRule.setContent {
            OverlayScreen(Screen.ABOUT_GAME, {}, {}, {}, {})
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
        var screen = Screen.values()[2]

        composeTestRule.setContent {
            OverlayScreen(screen, {}, {}, {}) { screen = Screen.values()[it] }
        }

        composeTestRule.onSpeechBox(expected[0].take(2).joinToString("\n"))
            .performClick()

        composeTestRule.waitForIdle()

        repeat(6) {
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