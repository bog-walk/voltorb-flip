package dev.bogwalk.ui.components.tiles

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.style.*

@Composable
fun FlipTile(
    screen: Screen,
    position: Pair<Int, Int>,
    value: Int,
    memo: List<Boolean> = List(4) { false },
    isInFocus: Boolean = false,
    isFlipped: Boolean = false,
    isMemoOpen: Boolean = false,
    onSelectRequest: (Pair<Int, Int>) -> Unit = {}
) {
    Tile(
        screen,
        position,
        if (isFlipped) TileState.FLIPPED else TileState.NOT_FLIPPED,
        isInFocus,
        isMemoOpen,
        onSelectRequest
    ) {
        if (isFlipped) {
            Icon(
                painter = painterResource("pixel_$value.svg"),
                contentDescription = "$FLIPPED_DESCR$value",
                modifier = Modifier.requiredSize(if (value == 0) 23.dp else 15.dp),
                tint = Color.Unspecified
            )
        } else {
            val alignments = listOf(
                Alignment.TopStart, Alignment.TopEnd, Alignment.BottomStart, Alignment.BottomEnd
            )

            if (memo[0]) {
                Canvas(Modifier
                    .testTag(ZERO_TAG)
                    .requiredSize(10.dp)
                    .align(alignments[0])
                    .offset(x = 5.dp, y = 5.dp)
                ) {
                    drawCircle(memoBrightYellow, 5.dp.toPx())
                }
            }
            for (i in 1..3) {
                if (memo[i]) {
                    Text(
                        text = i.toString(),
                        modifier = Modifier
                            .align(alignments[i])
                            .offset(x = (5 - i % 2 * 10).dp, y = (2 - i / 2 * 4).dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        if (isInFocus && isMemoOpen) {
            Icon(
                painter = painterResource(MEMO_PENCIL),
                contentDescription = MEMO_PENCIL_DESCR,
                modifier = Modifier.requiredSize(16.dp),
                tint = Color.Unspecified
            )
        }
    }
}

@Preview
@Composable
private fun FlipTilePreview() {
    VoltorbFlipTheme {
        Column {
            FlipTile(Screen.IN_GAME, 0 to 0, 0) // unflipped & not in focus
            FlipTile(Screen.IN_GAME, 0 to 1, 0, isInFocus = true) // unflipped & in focus
            FlipTile(Screen.IN_GAME, 2 to 0, 3,
                memo = List(4) { true }) // unflipped with stored memo
            FlipTile(Screen.IN_GAME, 2 to 0, 3,
                memo = List(4) { true }, isFlipped = true) // flipped with stored memo
            FlipTile(Screen.IN_GAME, 1 to 1, 1, isFlipped = true) // flipped with value
            FlipTile(Screen.IN_GAME, 3 to 2, 2, isInFocus = true, isFlipped = true) // last flipped
            FlipTile(Screen.IN_GAME, 1 to 3, 0, isFlipped = true) // flipped with zero
        }
    }
}

@Preview
@Composable
private fun FlipTileMemoOpenPreview() {
    VoltorbFlipTheme {
        Column {
            // unflipped with memo open & no stored memo
            FlipTile(Screen.IN_GAME, 2 to 0, 0, isMemoOpen = true)
            // unflipped with memo open & stored memo data
            FlipTile(Screen.IN_GAME, 2 to 0, 0,
                memo = List(4) { true }, isMemoOpen = true)
            // unflipped, in focus, with memo open & no stored memo
            FlipTile(Screen.IN_GAME, 2 to 0, 0, isInFocus = true, isMemoOpen = true)
            // unflipped, in focus, with memo open & stored memo data
            FlipTile(Screen.IN_GAME, 2 to 0, 0,
                memo = listOf(true, true, false, false),
                isInFocus = true, isMemoOpen = true)
            // flipped, with memo open & stored memo data
            FlipTile(Screen.IN_GAME, 2 to 0, 2,
                memo = listOf(true, true, false, false),
                isFlipped = true, isMemoOpen = true)
            // flipped, in focus, with memo open & stored memo data
            FlipTile(Screen.IN_GAME, 2 to 0, 2,
                memo = listOf(true, true, false, false),
                isInFocus = true, isFlipped = true, isMemoOpen = true)
        }
    }
}