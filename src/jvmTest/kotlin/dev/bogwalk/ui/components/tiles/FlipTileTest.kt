package dev.bogwalk.ui.components.tiles

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.style.FLIPPED_DESCR
import dev.bogwalk.ui.style.MEMO_PENCIL_DESCR
import dev.bogwalk.ui.style.TILE_TAG
import dev.bogwalk.ui.style.ZERO_TAG
import org.junit.Rule
import kotlin.test.Test

class FlipTileTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `FlipTile only displays content when flipped`() {
        val flipped = mutableStateOf(false)
        val content = mutableStateOf(0)

        composeTestRule.setContent {
            FlipTile(Screen.IN_GAME,0 to 0, content.value, isFlipped = flipped.value) {}
        }

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .assertExists()
            .assertIsEnabled()
            .onChildren()
            .assertCountEquals(0)

        flipped.value = true
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .assertIsNotEnabled()
            .onChild()
            .assertContentDescriptionEquals("${FLIPPED_DESCR}0")

        content.value = 3
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .assertIsNotEnabled()
            .onChild()
            .assertContentDescriptionEquals("$FLIPPED_DESCR${content.value}")
    }

    @Test
    fun `FlipTile shows marks if any stored & not flipped`() {
        val flipped = mutableStateOf(false)
        val padOpen = mutableStateOf(false)

        composeTestRule.setContent {
            FlipTile(Screen.IN_GAME,0 to 0, 1,
                listOf(true, true, false, false),
                isFlipped = flipped.value, isMemoOpen = padOpen.value) {}
        }

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .assertExists()
            .onChildren()
            .assertCountEquals(2)
            .assertAny(hasTestTag(ZERO_TAG))
            .assertAny(hasTextExactly("1"))

        padOpen.value = true
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .onChildren()
            .assertCountEquals(2)
            .assertAny(hasTestTag(ZERO_TAG))
            .assertAny(hasTextExactly("1"))

        flipped.value = true
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .onChild()
            .assertContentDescriptionEquals("${FLIPPED_DESCR }1")
    }

    @Test
    fun `FlipTile shows pencil icon if in focus regardless of flip state`() {
        val flipped = mutableStateOf(false)
        val focus = mutableStateOf(false)

        composeTestRule.setContent {
            FlipTile(Screen.IN_GAME,0 to 0, 1,
                listOf(true, false, false, false),
                isInFocus = focus.value, isFlipped = flipped.value, isMemoOpen = true) {}
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
            .assertAny(hasContentDescriptionExactly(MEMO_PENCIL_DESCR))
            .assertAny(hasTestTag(ZERO_TAG))

        flipped.value = true
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(TILE_TAG, useUnmergedTree = true)
            .onChildren()
            .assertCountEquals(2)
            .assertAny(hasContentDescriptionExactly(MEMO_PENCIL_DESCR))
            .assertAny(hasContentDescriptionExactly("${FLIPPED_DESCR}1"))
    }
}