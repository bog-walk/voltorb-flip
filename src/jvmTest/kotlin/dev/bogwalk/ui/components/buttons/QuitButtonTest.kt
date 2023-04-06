package dev.bogwalk.ui.components.buttons

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.style.QUIT
import org.junit.Rule
import kotlin.test.Test

class QuitButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `QuitButton is disabled if position set to info screen`() {
        val position = mutableStateOf(-1 to -1)

        composeTestRule.setContent {
            QuitButton(position.value, false) {}
        }

        composeTestRule.onNodeWithText(QUIT)
            .assertExists()
            .assertIsEnabled()

        position.value = -2 to -2
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText(QUIT)
            .assertExists()
            .assertIsNotEnabled()
    }
}