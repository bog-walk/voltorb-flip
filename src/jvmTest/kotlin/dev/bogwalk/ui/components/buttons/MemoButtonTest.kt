package dev.bogwalk.ui.components.buttons

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.style.CLOSE
import dev.bogwalk.ui.style.MEMO_TAG
import dev.bogwalk.ui.style.OPEN
import dev.bogwalk.ui.style.MEMO_X_DESCR
import org.junit.Rule
import kotlin.test.Test

class MemoButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `MemoButton is disabled if position set to info screen`() {
        val state = mutableStateOf(Screen.IN_GAME)

        composeTestRule.setContent {
            MemoButton(state.value, false) {}
        }

        composeTestRule.onNodeWithTag(MEMO_TAG)
            .assertExists()
            .assertIsEnabled()

        state.value = Screen.PRE_GAME
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(MEMO_TAG)
            .assertIsNotEnabled()
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
            .assertAny(hasTextExactly(OPEN))
            .filterToOne(hasContentDescriptionExactly(MEMO_X_DESCR))
            .assertExists()

        isMemoOpen.value = true
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(MEMO_TAG, useUnmergedTree = true)
            .assertExists()
            .onChildren()
            .assertAny(hasTextExactly(CLOSE))
    }
}