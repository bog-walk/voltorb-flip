package dev.bogwalk.ui.components.buttons

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.style.INFO
import dev.bogwalk.ui.style.OPTIONS_TAG
import dev.bogwalk.ui.style.RETURN
import org.junit.Rule
import kotlin.test.Test

class OptionsPanelTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `OptionsPanel switches content when info requested`() {
        composeTestRule.setContent {
            OptionsPanel({}, {}, {})
        }

        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(3)
            .assertAll(isEnabled())
            .filterToOne(hasTextExactly(INFO))
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(4)
            .assertAll(isEnabled())
            .filterToOne(hasTextExactly(RETURN))
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(OPTIONS_TAG)
            .assertCountEquals(3)
            .assertAll(isEnabled())
    }
}