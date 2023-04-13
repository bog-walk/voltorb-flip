package dev.bogwalk.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import dev.bogwalk.ui.style.*

internal fun ComposeContentTestRule.assertGameScreenDisabled() {
    onNodeWithTag(GRID_TAG)
        .onChildren()
        .filter(hasTestTag(TILE_TAG))
        .assertAll(isNotEnabled())

    onNodeWithTag(MEMO_TAG)
        .assertIsNotEnabled()

    onAllNodesWithTag(MEMO_PAD_TAG)
        .assertCountEquals(0)

    onAllNodesWithText(QUIT)
        .filterToOne(!hasTestTag(OPTIONS_TAG))
        .assertIsNotEnabled()
}

internal fun ComposeContentTestRule.onSpeechBox(
    line: String? = null
): SemanticsNodeInteraction {
    return line?.let {
        onNodeWithTag(SPEECH_TAG)
            .onChildren()
            .assertAny(hasTextExactly(it))
            .filterToOne(hasContentDescriptionExactly(NEXT_ARROW_DESCR))
    } ?:
    onNodeWithTag(SPEECH_TAG)
        .onChildren()
        .filterToOne(hasContentDescriptionExactly(NEXT_ARROW_DESCR))
}