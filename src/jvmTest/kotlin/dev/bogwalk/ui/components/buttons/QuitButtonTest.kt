package dev.bogwalk.ui.components.buttons

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.style.*
import org.junit.Rule
import kotlin.test.Test

class QuitButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `QuitButton is enabled in only 1 screen state`() {
        val screen = mutableStateOf(Screen.IN_GAME)

        composeTestRule.setContent {
            QuitButton(screen.value, 0 to 0, false) {}
        }

        composeTestRule.onNodeWithText(QUIT)
            .assertExists()
            .assertIsEnabled()

        for (screenType in Screen.values()) {
            if (screenType == Screen.IN_GAME) continue

            screen.value = screenType
            composeTestRule.waitForIdle()

            composeTestRule.onNodeWithText(QUIT)
                .assertIsNotEnabled()
        }
    }

    @Test
    fun `QuitOption switches focus by clicking buttons`() {
        composeTestRule.setContent {
            QuitOption(YES) {}
        }

        composeTestRule.onNodeWithText(YES)
            .assertIsEnabled()
            .assertIsNotFocused()
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(YES)
            .assertIsFocused()
    }
}