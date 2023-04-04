package dev.bogwalk.ui.components.tiles

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.drawInFocusBorders
import dev.bogwalk.ui.util.drawInsetBackground
import dev.bogwalk.ui.util.drawLineBorder

enum class TileState {
    STATIC_INFO, NOT_FLIPPED, FLIPPED
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tile(
    position: Pair<Int, Int>,
    tileState: TileState,
    isInFocus: Boolean = false,
    isMemoOpen: Boolean = false,
    onSelectRequest: (Pair<Int, Int>) -> Unit = {},
    content: @Composable (BoxScope.() -> Unit)
) {
    Box(
        modifier = Modifier
            .semantics(mergeDescendants = true) {
                role = Role.Button
                if (tileState != TileState.NOT_FLIPPED) disabled()
            }
            .padding(7.dp)
            .requiredSize(42.dp)
            .background(color = when (tileState) {
                TileState.STATIC_INFO -> rowColors[position.first]
                TileState.NOT_FLIPPED -> darkGreen
                TileState.FLIPPED -> lightRed
            })
            .drawBehind {
                when (tileState) {
                    TileState.STATIC_INFO -> {
                        drawLineBorder(outerColor = greenWhite)
                        drawLine(greenWhite,
                            Offset(0f, size.height / 3 + 2),
                            Offset(size.width, size.height / 3 + 2),
                            4.dp.toPx())
                    }
                    else -> {
                        if (isInFocus) {
                            drawConnectors(position)
                            drawInFocusBorders(isMemoOpen)
                        } else {
                            drawLineBorder(outerColor = greenWhite)
                            if (position != Pair(-1, -1)) drawConnectors(position)
                            drawLineBorder(innerColor = slateGrey2)
                        }
                        if (tileState == TileState.NOT_FLIPPED) {
                            drawInsetBackground()
                        } else {
                            drawLineBorder()
                        }
                    }
                }
            }
            .onClick(
                enabled = tileState == TileState.NOT_FLIPPED && position != -1 to -1
            ) { onSelectRequest(position) },
        contentAlignment = Alignment.Center,
        content = content
    )
}

// these should be drawn on top of everything but an in-focus border
// currently only behaves correctly at the point of origin, but will always
// be drawn below the next composed tile
// Draw after all tiles composed??? OR
// Draw on top of all tiles, but then in-focus border drawn over top of normal border???
private fun DrawScope.drawConnectors(position: Pair<Int, Int>) {
    val colorSize = 3.5.dp.toPx()

    if (position.first != -1) {
        drawRect(greenWhite,
            Offset(x = size.width, y = size.height / 3),
            Size(size.width / 3, size.height / 3)
        )
        drawRect(
            rowColors[position.first],
            Offset(x = size.width, y = size.height / 2 - colorSize),
            Size(size.width / 3, colorSize * 2)
        )
    }
    if (position.second != -1) {
        drawRect(greenWhite,
            Offset(x = size.width / 3, y = size.height),
            Size(size.width / 3, size.height / 3)
        )
        drawRect(
            rowColors[position.second],
            Offset(x = size.width / 2 - colorSize, y = size.height),
            Size(colorSize * 2, size.height / 3)
        )
    }
}

@Preview
@Composable
private fun TilePreview() {
    VoltorbFlipTheme {
        Column {
            Tile(0 to 0, TileState.STATIC_INFO) {}
            Tile(0 to 1, TileState.NOT_FLIPPED) {}
            Tile(0 to 1, TileState.NOT_FLIPPED, isInFocus = true) {}
            Tile(0 to 4, TileState.NOT_FLIPPED, isInFocus = true, isMemoOpen = true) {}
            Tile(0 to 2, TileState.FLIPPED) {}
            Tile(0 to 3, TileState.FLIPPED, isInFocus = true) {}
            Tile(0 to 4, TileState.FLIPPED, isInFocus = true, isMemoOpen = true) {}
        }
    }
}

@Preview
@Composable
private fun TileConnectorPreview() {
    VoltorbFlipTheme {
        Column {
            Row {
                FlipTile(3 to 3, 0)
                FlipTile(3 to 4, 1, isFlipped = true)
                InfoTile(3, 4, 1)
            }
            Row {
                FlipTile(4 to 3, 0)
                FlipTile(4 to 4, 0)
                InfoTile(4, 8, 1)
            }
            Row {
                InfoTile(3, 4, 2)
                InfoTile(4, 6, 1)
            }
        }
    }
}