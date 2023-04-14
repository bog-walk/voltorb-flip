package dev.bogwalk.ui.components.buttons

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.style.*
import org.junit.Rule
import kotlin.test.Test

class OptionsPanelTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `OptionsPanel switches content based on screen state`() {
        val screen = mutableStateOf(Screen.PRE_GAME)

        composeTestRule.setContent {
            OptionsPanel(screen.value, {}, {}, {})
        }

        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(3)
            .assertAll(isEnabled())
            .filterToOne(hasTextExactly(INFO))

        screen.value = Screen.PRE_INFO
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(4)
            .assertAll(isEnabled())
            .filterToOne(hasTextExactly(RETURN))
    }

    @Test
    fun `OptionsPanel switches focus by clicking buttons`() {
        composeTestRule.setContent {
            OptionsPanel(Screen.PRE_INFO, {}, {}, {})
        }

        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(4)
            .assertAll(isNotFocused())
            .filterToOne(hasTextExactly(HOW_TO))
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .filterToOne(hasTextExactly(HOW_TO))
            .assertIsFocused()

        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .filterToOne(hasTextExactly(HINT))
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .filterToOne(hasTextExactly(HINT))
            .assertIsFocused()
    }
}