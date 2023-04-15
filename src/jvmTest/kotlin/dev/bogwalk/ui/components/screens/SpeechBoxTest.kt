package dev.bogwalk.ui.components.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.onSpeechBox
import dev.bogwalk.ui.style.ABOUT_TEXT
import dev.bogwalk.ui.style.HOW_TO_TEXT
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals

class SpeechBoxTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `SpeechBox iterates through text when clicked`() {
        val expected = HOW_TO_TEXT.split("|").map { it.split("\n") }

        composeTestRule.setContent {
            OverlayScreen(Screen.ABOUT_GAME, 1, 0, {}, {}, {}, {}, {}, {}, {})
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
            OverlayScreen(screen, 1, 0, {}, {}, {}, {}, {}, { screen = Screen.values()[it] }, {})
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