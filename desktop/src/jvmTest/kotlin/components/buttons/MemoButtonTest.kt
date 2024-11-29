package components.buttons

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.components.buttons.MemoButton
import dev.bogwalk.ui.style.*
import org.junit.Rule
import kotlin.test.Test

class MemoButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `MemoButton is enabled in only 1 screen state`() {
        val screen = mutableStateOf(Screen.IN_GAME)

        composeTestRule.setContent {
            MemoButton(screen.value, false) {}
        }

        composeTestRule.onNodeWithTag(MEMO_TAG)
            .assertExists()
            .assertIsEnabled()

        for (screenType in Screen.entries) {
            if (screenType == Screen.IN_GAME) continue

            screen.value = screenType
            composeTestRule.waitForIdle()

            composeTestRule.onNodeWithTag(MEMO_TAG)
                .assertIsNotEnabled()
        }
    }

    @Test
    fun `MemoButton text changes depending on memo pad state`() {
        val isMemoOpen = mutableStateOf(false)

        composeTestRule.setContent {
            MemoButton(Screen.IN_GAME, isMemoOpen.value) {}
        }

        composeTestRule.onNodeWithTag(MEMO_TAG, useUnmergedTree = true)
            .assertExists()
            .onChildren()
            .assertCountEquals(3)
            .assertAny(hasTextExactly(OPEN))
            .assertAny(hasContentDescriptionExactly(MEMO_X_DESCR))

        isMemoOpen.value = true
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(MEMO_TAG, useUnmergedTree = true)
            .onChildren()
            .assertCountEquals(3)
            .filterToOne(hasTextExactly(CLOSE))
    }
}