package dev.bogwalk.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
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

@Composable
fun FlipTile(
    value: Int,
    rowIndex: Int,
    colIndex: Int,
    memo: BooleanArray = BooleanArray(4),
    isInFocus: Boolean = false,
    isFlipped: Boolean = false,
    isMemoOpen: Boolean = false,
    inGameGrid: Boolean = true,
    onFlipRequest: () -> Unit = {}
) {
    Tile(rowIndex, colIndex,
        if (isFlipped) TileState.FLIPPED else TileState.NOT_FLIPPED,
        isInFocus, isMemoOpen, inGameGrid, onFlipRequest) {
        if (isFlipped) {
            if (value == 0) {
                Icon(
                    painter = painterResource("voltorb_outlined.svg"),
                    contentDescription = "Voltorb",
                    modifier = Modifier.requiredSize(29.dp),
                    tint = Color.Unspecified
                )
            } else {
                // [REPLACE with pixelated font]
                Text(value.toString(), style = MaterialTheme.typography.labelSmall)
            }
        } else if (isMemoOpen) {
            if (memo[0]) {  // surely a circle can be drawn (waste of canvas?)
                Icon(
                    painter = painterResource("circle.svg"),
                    contentDescription = "Voltorb",
                    modifier = Modifier.requiredSize(11.dp)
                        .align(Alignment.TopStart).offset(x=9.dp, y=9.dp),
                    tint = Color.Unspecified
                )
            }
            if (memo[1]) {
                Text("1", Modifier.align(Alignment.TopEnd).offset(x=(-9).dp, y=3.5.dp),
                    style = MaterialTheme.typography.bodySmall)
            }
            if (memo[2]) {  // is alignment order correct?
                Text("2", Modifier.align(Alignment.BottomStart).offset(x=10.dp, y=(-5).dp),
                    style = MaterialTheme.typography.bodySmall)
            }
            if (memo[3]) {  // is alignment order correct?
                Text("3", Modifier.align(Alignment.BottomEnd).offset(x=(-9).dp, y=(-5).dp),
                    style = MaterialTheme.typography.bodySmall)
            }
            if (isInFocus) {
                Icon(
                    painter = painterResource("memo_pencil.svg"),
                    contentDescription = "Pencil",
                    modifier = Modifier.requiredSize(20.dp),
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
            FlipTile(0, 0, 0) // unflipped & not in focus
            FlipTile(0, 0, 1, isInFocus = true) // unflipped & in focus
            FlipTile(1, 1, 1, isFlipped = true) // flipped with value
            FlipTile(2, 3, 2, isInFocus = true, isFlipped = true) // last flipped
            FlipTile(3, 0, 4, isFlipped = true) // flipped with value
            FlipTile(0, 1, 3, isFlipped = true) // flipped with voltorb
        }
    }
}

@Preview
@Composable
private fun FlipTileMemoPreview() {
    VoltorbFlipTheme {
        Column {
            // unflipped with memo open & no stored memo
            FlipTile(0, 2, 0, isMemoOpen = true)
            // unflipped with memo open & stored memo data
            FlipTile(0, 2, 0,
                memo = booleanArrayOf(true, true, true, true), isMemoOpen = true)
            // unflipped, in focus, with memo open & no stored memo
            FlipTile(0, 2, 1, isInFocus = true, isMemoOpen = true)
            // unflipped, in focus, with memo open & stored memo data
            FlipTile(0, 2, 1, memo = booleanArrayOf(true, true, false, false),
                isInFocus = true, isMemoOpen = true)
        }
    }
}