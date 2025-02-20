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

internal fun ComposeContentTestRule.assertGameScreenEnabled() {
    onNodeWithTag(GRID_TAG)
        .onChildren()
        .filter(hasTestTag(TILE_TAG))
        .assertCountEquals(35)
        .filter(isEnabled())
        .assertCountEquals(25)

    onNodeWithTag(MEMO_TAG)
        .assertIsEnabled()

    onAllNodesWithText(QUIT)
        .filterToOne(!hasTestTag(OPTIONS_TAG))
        .assertIsEnabled()
}

/**
 * Asserts the presence of a SpeechBox with an IconButton as one of its children.
 *
 * @param line Asserts that child Text matches this exactly, if provided.
 */
internal fun ComposeContentTestRule.onSpeechBox(
    line: String? = null
): SemanticsNodeInteraction {
    return line?.let {
        onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
            .onChildren()
            .assertAny(hasTextExactly(it))
            .filterToOne(hasAnyChild(hasContentDescriptionExactly(NEXT_ARROW_DESCR)))
            .assertIsEnabled()
    } ?:
    onNodeWithTag(SPEECH_TAG, useUnmergedTree = true)
        .onChildren()
        .filterToOne(hasAnyChild(hasContentDescriptionExactly(NEXT_ARROW_DESCR)))
        .assertIsEnabled()
}

internal fun ComposeContentTestRule.performClickToPlay() {
    onAllNodesWithTag(OPTIONS_TAG)
        .filterToOne(hasTextExactly(PLAY))
        .performClick()
}