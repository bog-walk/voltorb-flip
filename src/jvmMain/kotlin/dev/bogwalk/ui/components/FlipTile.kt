package dev.bogwalk.ui.components

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.style.VoltorbFlipTheme
import dev.bogwalk.ui.style.memoBrightYellow

@Composable
fun FlipTile(
    position: Pair<Int, Int>,
    value: Int,
    memo: BooleanArray = BooleanArray(4),
    isInFocus: Boolean = false,
    isFlipped: Boolean = false,
    isMemoOpen: Boolean = false,
    onSelectRequest: (Pair<Int, Int>) -> Unit = {}
) {
    Tile(
        position,
        if (isFlipped) TileState.FLIPPED else TileState.NOT_FLIPPED,
        isInFocus,
        isMemoOpen,
        onSelectRequest
    ) {
        if (isFlipped) {
            if (value == 0) {
                Icon(
                    painter = painterResource("zero_outlined.svg"),
                    contentDescription = null,
                    modifier = Modifier.requiredSize(23.dp),
                    tint = Color.Unspecified
                )
            } else {
                Text(
                    text = value.toString(),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
        if (isMemoOpen) {  // flipped tiles still show memo pen even if all pad buttons disabled
            val alignments = listOf(
                Alignment.TopStart, Alignment.TopEnd, Alignment.BottomStart, Alignment.BottomEnd
            )

            if (memo[0]) {
                Canvas(
                    modifier = Modifier
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
            if (isInFocus) {
                Icon(
                    painter = painterResource("memo_pencil.svg"),
                    contentDescription = null,
                    modifier = Modifier.requiredSize(16.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Preview
@Composable
private fun FlipTilePreview() {
    VoltorbFlipTheme {
        Column {
            FlipTile(0 to 0, 0) // unflipped & not in focus
            FlipTile(0 to 1, 0, isInFocus = true) // unflipped & in focus
            FlipTile(1 to 1, 1, isFlipped = true) // flipped with value
            FlipTile(3 to 2, 2, isInFocus = true, isFlipped = true) // last flipped
            FlipTile(0 to 4, 3, isFlipped = true) // flipped with value
            FlipTile(1 to 3, 0, isFlipped = true) // flipped with voltorb
        }
    }
}

@Preview
@Composable
private fun FlipTileMemoPreview() {
    VoltorbFlipTheme {
        Column {
            // unflipped with memo open & no stored memo
            FlipTile(2 to 0, 0, isMemoOpen = true)
            // unflipped with memo open & stored memo data
            FlipTile(2 to 0, 0,
                memo = booleanArrayOf(true, true, true, true), isMemoOpen = true)
            // unflipped, in focus, with memo open & no stored memo
            FlipTile(2 to 0, 0, isInFocus = true, isMemoOpen = true)
            // unflipped, in focus, with memo open & stored memo data
            FlipTile(2 to 0, 0, memo = booleanArrayOf(true, true, false, false),
                isInFocus = true, isMemoOpen = true)
        }
    }
}