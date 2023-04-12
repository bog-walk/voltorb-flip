package dev.bogwalk.ui.components.buttons

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
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
        val inGame = mutableStateOf(true)

        composeTestRule.setContent {
            MemoButton(false, inGame.value) {}
        }

        composeTestRule.onNodeWithTag(MEMO_TAG)
            .assertExists()
            .assertIsEnabled()

        inGame.value = false
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(MEMO_TAG)
            .assertExists()
            .assertIsNotEnabled()
    }

    @Test
    fun `MemoButton text changes depending on memo pad state`() {
        val isMemoOpen = mutableStateOf(false)

        composeTestRule.setContent {
            MemoButton(isMemoOpen.value, true) {}
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