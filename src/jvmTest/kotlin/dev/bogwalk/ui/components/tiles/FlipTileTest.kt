package dev.bogwalk.ui.components.tiles

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.style.PIXEL_PENCIL_DESCR
import dev.bogwalk.ui.style.TILE_TAG
import dev.bogwalk.ui.style.ZERO_OUTLINED_DESCR
import dev.bogwalk.ui.style.ZERO_TAG
import org.junit.Rule
import kotlin.test.Test

class FlipTileTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `FlipTile only displays content when flipped`() {
        val state = mutableStateOf(false)
        val content = mutableStateOf(0)

        composeTestRule.setContent {
            FlipTile(0 to 0, content.value, isFlipped = state.value) {}
        }

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .assertExists()
            .assertIsEnabled()
            .onChildren()
            .assertCountEquals(0)

        state.value = true
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .assertIsNotEnabled()
            .onChildren()
            .assertCountEquals(1)
            .assertAll(hasContentDescriptionExactly(ZERO_OUTLINED_DESCR))

        content.value = 3
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .assertIsNotEnabled()
            .onChildren()
            .assertCountEquals(1)
            .assertAll(hasTextExactly(content.value.toString()))
    }

    @Test
    fun `FlipTile shows marks if memo opened`() {
        val marks = mutableStateOf(booleanArrayOf(true, true, false, false))
        val padOpen = mutableStateOf(false)

        composeTestRule.setContent {
            FlipTile(0 to 0, 1, marks.value, isMemoOpen = padOpen.value) {}
        }

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .assertExists()
            .onChildren()
            .assertCountEquals(0)

        padOpen.value = true
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .onChildren()
            .assertCountEquals(2)
            .assertAny(hasTestTag(ZERO_TAG))
            .assertAny(hasTextExactly("1"))

        marks.value = booleanArrayOf(true, true, true, true)
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .onChildren()
            .assertCountEquals(4)
            .assertAny(hasTextExactly("3"))
    }

    @Test
    fun `FlipTile does not shows marks if flipped with memo open`() {
        val state = mutableStateOf(false)

        composeTestRule.setContent {
            FlipTile(0 to 0, 0, booleanArrayOf(true, false, false, false),
                isFlipped = state.value, isMemoOpen = true) {}
        }

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .assertExists()
            .onChildren()
            .assertCountEquals(1)
            .filterToOne(hasTestTag(ZERO_TAG))

        state.value = false
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .onChildren()
            .assertCountEquals(1)
            .filterToOne(hasContentDescriptionExactly(ZERO_OUTLINED_DESCR))
    }

    @Test
    fun `FlipTile shows pencil icon if in focus regardless of flip state`() {
        val state = mutableStateOf(false)
        val focus = mutableStateOf(false)

        composeTestRule.setContent {
            FlipTile(0 to 0, 1, booleanArrayOf(true, false, false, false),
                isInFocus = focus.value, isFlipped = state.value, isMemoOpen = true) {}
        }

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .assertExists()
            .onChildren()
            .assertCountEquals(1)
            .filterToOne(hasTestTag(ZERO_TAG))

        focus.value = true
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .onChildren()
            .assertCountEquals(2)
            .assertAny(hasContentDescriptionExactly(PIXEL_PENCIL_DESCR))

        state.value = true
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .onChildren()
            .assertCountEquals(2)
            .assertAny(hasContentDescriptionExactly(PIXEL_PENCIL_DESCR))
            .assertAny(hasTextExactly("1"))
    }
}