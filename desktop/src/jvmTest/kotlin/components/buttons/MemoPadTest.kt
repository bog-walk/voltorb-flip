package components.buttons

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.components.buttons.MemoPad
import dev.bogwalk.ui.style.MEMO_ARROW_DESCR
import dev.bogwalk.ui.style.MEMO_PAD_TAG
import dev.bogwalk.ui.style.MEMO_ZERO_INACTIVE_DESCR
import org.junit.Rule
import kotlin.test.Test

class MemoPadTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `MemoPad loads with correct content`() {
        composeTestRule.setContent {
            MemoPad(Screen.IN_GAME, List(4) { false }, {}) {}
        }

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(5)
            .assertAny(hasContentDescriptionExactly(MEMO_ARROW_DESCR))
            .assertAny(hasContentDescriptionExactly(MEMO_ZERO_INACTIVE_DESCR))
            .assertAny(hasTextExactly("1"))
            .assertAny(hasTextExactly("2"))
            .assertAny(hasTextExactly("3"))
    }

    @Test
    fun `MemoPad buttons all but 1 disabled when no array provided`() {
        val array: MutableState<List<Boolean>?> = mutableStateOf(List(4) { false })

        composeTestRule.setContent {
            MemoPad(Screen.IN_GAME, array.value, {}) {}
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
    fun `MemoPad buttons only enabled in 1 screen state`() {
        val screen = mutableStateOf(Screen.IN_GAME)

        composeTestRule.setContent {
            MemoPad(screen.value, List(4) { false }, {}) {}
        }

        composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
            .assertCountEquals(5)
            .assertAll(isEnabled())

        for (screenType in Screen.entries) {
            if (screenType == Screen.IN_GAME) continue

            screen.value = screenType
            composeTestRule.waitForIdle()

            composeTestRule.onAllNodesWithTag(MEMO_PAD_TAG)
                .assertCountEquals(5)
                .assertAll(isNotEnabled())
        }
    }
}