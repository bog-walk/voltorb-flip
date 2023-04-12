package dev.bogwalk.ui.components.buttons

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.style.MEMO_PAD_TAG
import dev.bogwalk.ui.style.MEMO_ZERO_DESCR
import dev.bogwalk.ui.style.MEMO_ARROW_DESCR
import org.junit.Rule
import kotlin.test.Test

class MemoPadTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `MemoPad loads with correct children`() {
        composeTestRule.setContent {
            MemoPad(BooleanArray(4), true, {}) {}
        }

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(5)
            .assertAny(hasContentDescriptionExactly(MEMO_ARROW_DESCR))
            .assertAny(hasContentDescriptionExactly(MEMO_ZERO_DESCR))
            .assertAny(hasTextExactly("1"))
            .assertAny(hasTextExactly("2"))
            .assertAny(hasTextExactly("3"))
    }

    @Test
    fun `all MemoPad buttons except return disabled when no array provided`() {
        val array: MutableState<BooleanArray?> = mutableStateOf(BooleanArray(4))

        composeTestRule.setContent {
            MemoPad(array.value, inGameUse = true, {}) {}
        }

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(5)
            .assertAll(isEnabled())

        array.value = null
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(5)
            .filterToOne(isEnabled())
            .assertContentDescriptionEquals(MEMO_ARROW_DESCR)
    }

    @Test
    fun `all MemoPad buttons disabled when used in info screen`() {
        val inGameUse = mutableStateOf(true)

        composeTestRule.setContent {
            MemoPad(BooleanArray(4), inGameUse.value, {}) {}
        }

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(5)
            .assertAll(isEnabled())

        inGameUse.value = false
        composeTestRule.waitForIdle()

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(5)
            .assertAll(isNotEnabled())
    }
}