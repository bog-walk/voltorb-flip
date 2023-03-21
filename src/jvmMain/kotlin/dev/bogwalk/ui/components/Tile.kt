package dev.bogwalk.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.style.*

enum class TileState {
    STATIC_INFO, NOT_FLIPPED, FLIPPED
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tile(
    rowIndex: Int,
    colIndex: Int,
    tileState: TileState,
    isInFocus: Boolean = false,
    memoIsOpen: Boolean = false,
    onFlipRequest: () -> Unit = {},
    content: @Composable (BoxScope.() -> Unit)
) {
    Box(
        modifier = Modifier
            .semantics(mergeDescendants = true) {
                role = Role.Button
            }
            .padding(10.dp).requiredSize(56.dp)
            .background(color = when (tileState) {
                TileState.STATIC_INFO -> rowColors[rowIndex]
                TileState.NOT_FLIPPED -> darkGreen
                TileState.FLIPPED -> lightRed
            })
            .drawWithCache {
                onDrawBehind {
                    when (tileState) {
                        TileState.STATIC_INFO -> {
                            drawTileBorder(false, true, false, false)
                            drawLine(greenWhite, Offset(0f, 22f), Offset(size.width, 22f), 6.6f)
                        }
                        TileState.NOT_FLIPPED -> {
                            if (isInFocus) {
                                drawConnectors(rowIndex, colIndex)
                                drawTileBorder(true, true, false, memoIsOpen)
                            } else {
                                drawTileBorder(false, true, false, memoIsOpen)
                                drawConnectors(rowIndex, colIndex)
                            }
                            drawTileBorder(isInFocus, false, true, memoIsOpen)
                            drawInsetBackground()
                        }
                        TileState.FLIPPED -> {
                            if (isInFocus) {
                                drawConnectors(rowIndex, colIndex)
                                drawTileBorder(true, true, false, false)
                            } else {
                                drawTileBorder(false, true, false, false)
                                drawConnectors(rowIndex, colIndex)
                            }
                            drawTileBorder(isInFocus, false, true, false)
                            drawTileBorder(false, false, false, false)
                        }
                    }
                }
            }
            .onClick(enabled = tileState == TileState.NOT_FLIPPED) { onFlipRequest() },
        contentAlignment = Alignment.Center,
        content = content
    )
}

private fun DrawScope.drawTileBorder(
    isInFocus: Boolean,
    isOuter: Boolean,
    isInner: Boolean,
    memoIsOpen: Boolean
) {
    val cap = StrokeCap.Round
    val (stroke, extra, color) = when {
        isOuter && !isInFocus -> Triple(6.6f, 0.8f, greenWhite)
        isOuter && isInFocus -> if (memoIsOpen) {
            Triple(3f, 0f, memoBrown)
        } else Triple(3f, 0f, darkRed)
        isInner && !isInFocus -> Triple(3f, 5.4f, slateGrey2)
        isInner && isInFocus -> if (memoIsOpen) {
            Triple(7f, 3.8f, memoYellow)
        } else Triple(7f, 3.8f, brightRed)
        // nested flipped border
        else -> Triple(3f, 7.8f, mediumRed)
    }

    drawLine(color, Offset(extra, extra), Offset(size.width - extra, extra), stroke, cap)
    drawLine(color, Offset(size.width - extra, extra),
        Offset(size.width - extra, size.height - extra), stroke, cap)
    drawLine(color, Offset(extra, extra),
        Offset(extra, size.height - extra), stroke, cap)
    drawLine(color, Offset(extra, size.height - extra),
        Offset(size.width - extra, size.height - extra), stroke, cap)
}

private fun DrawScope.drawInsetBackground() {
    inset(9f) {
        drawRect(lightGreen,
            Offset(x = size.width / 3f, y = 0f),
            Size(size.width / 3f, size.height / 3f))
        drawRect(lightGreen,
            Offset(x = size.width / 3f, y = size.height / 3f * 2f),
            Size(size.width / 3f, size.height / 3f))
        drawRect(lightGreen,
            Offset(x = 0f, y = size.height / 3f),
            Size(size.height / 3f, size.width / 3f))
        drawRect(lightGreen,
            Offset(x = size.width / 3f * 2f, y = size.height / 3f),
            Size(size.height / 3f, size.width / 3f))
    }
}

// these should be drawn on top of everything but an in-focus border
// currently only behaves correctly at the point of origin, but will always
// be drawn below the next composed tile
// Draw after all tiles composed??? OR
// Draw on top of all tiles, but then in-focus border drawn over top of normal border???
private fun DrawScope.drawConnectors(rowI: Int, colI: Int) {
    drawRect(greenWhite, Offset(x = size.width, y = size.height / 3f),
        Size(size.height / 3f, size.width / 3f)
    )
    drawRect(greenWhite, Offset(x = size.width / 3f, y = size.height),
        Size(size.width / 3f, size.height / 3f)
    )
    drawRect(
        rowColors[rowI], Offset(x = size.width, y = size.height / 2 - 4f),
        Size(size.height / 3f, 8f)
    )
    drawRect(
        rowColors[colI], Offset(x = size.width / 2 - 4f, y = size.height),
        Size(8f, size.height / 3f)
    )
}

@Preview
@Composable
private fun TilePreview() {
    VoltorbFlipTheme {
        Column {
            Tile(0, 0, TileState.STATIC_INFO) {}
            Tile(0, 1, TileState.NOT_FLIPPED) {}
            Tile(0, 2, TileState.FLIPPED) {}
            Tile(0, 3, TileState.NOT_FLIPPED, isInFocus = true) {}
            // should this be possible?
            Tile(0, 4, TileState.FLIPPED, isInFocus = true) {}
            Tile(2, 1, TileState.NOT_FLIPPED, isInFocus = true, memoIsOpen = true) {}
        }
    }
}

@Preview
@Composable
private fun TileConnectorPreview() {
    VoltorbFlipTheme {
        Column {
            Row {
                FlipTile(0, 3, 3)
                FlipTile(1, 3, 4, isFlipped = true)
                InfoTile(4, 1, 3)
            }
            Row {
                FlipTile(0, 4, 3)
                FlipTile(0, 4, 4)
                InfoTile(8, 1, 4)
            }
            Row {
                InfoTile(4, 2, 3)
                InfoTile(6, 1, 4)
            }
        }
    }
}