package dev.bogwalk.ui.components.buttons

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.style.QUIT
import org.junit.Rule
import kotlin.test.Test

class QuitButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `QuitButton is disabled if used in info screen`() {
        val state = mutableStateOf(Screen.IN_GAME)

        composeTestRule.setContent {
            QuitButton(state.value, 0 to 0, false) {}
        }

        composeTestRule.onNodeWithText(QUIT)
            .assertExists()
            .assertIsEnabled()

        state.value = Screen.ABOUT_GAME
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(QUIT)
            .assertIsNotEnabled()
    }
}