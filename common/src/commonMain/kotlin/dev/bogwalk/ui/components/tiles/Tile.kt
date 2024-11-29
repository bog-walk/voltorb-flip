package dev.bogwalk.ui.components.tiles

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.drawInFocusBorders
import dev.bogwalk.ui.util.drawLineBorder

enum class TileState {
    STATIC_INFO, NOT_FLIPPED, FLIPPED
}

@Composable
fun Tile(
    screen: Screen,
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
                if (tileState != TileState.NOT_FLIPPED ||
                    screen != Screen.IN_GAME) disabled()
            }
            .testTag(TILE_TAG)
            .padding(7.dp)
            .requiredSize(tileSize)
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
                            thickBorder.toPx())
                    }
                    else -> {
                        if (isInFocus) {
                            drawConnectors(position)
                            drawInFocusBorders(isMemoOpen)
                        } else {
                            drawLineBorder(outerColor = greenWhite)
                            if (position != -1 to -1) drawConnectors(position)
                            drawLineBorder(innerColor = slateGrey1)
                        }
                        if (tileState == TileState.NOT_FLIPPED) {
                            drawInsetBackground()
                        } else {
                            drawLineBorder()
                        }
                    }
                }
            }
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,  // this prevents mouse hover effect
                enabled = tileState == TileState.NOT_FLIPPED && screen == Screen.IN_GAME,
                role = Role.Button
            ) { onSelectRequest(position) },
        contentAlignment = Alignment.Center,
        content = content
    )
}

/**
 * Draws the internal checker pattern used for unflipped tiles.
 */
private fun DrawScope.drawInsetBackground() {
    inset(5.5.dp.toPx()) {
        val xThird = size.width / 3
        val yThird = size.height / 3

        drawRect(lightGreen,
            Offset(xThird,0f),
            Size(xThird, yThird)
        )
        drawRect(lightGreen,
            Offset(xThird, yThird * 2),
            Size(xThird, yThird)
        )
        drawRect(lightGreen,
            Offset(0f, yThird),
            Size(yThird, xThird)
        )
        drawRect(lightGreen,
            Offset(xThird * 2, yThird),
            Size(yThird, xThird)
        )
    }
}

/**
 * Draws the left (row) and bottom (column) grid connectors based on [position].
 *
 * Technically, these should be drawn on top of everything but an in-focus border currently only
 * behaves correctly at the point of origin, and will always be drawn below the next composed tile.
 * Consider alternatives: draw after all tiles composed? draw on top of all tiles then draw
 * in-focus border on top of default border?
 */
private fun DrawScope.drawConnectors(position: Pair<Int, Int>) {
    val colorSize = 3.5.dp.toPx()
    val xThird = size.width / 3
    val yThird = size.height / 3

    if (position.first != -1) {
        drawRect(greenWhite,
            Offset(size.width, yThird),
            Size(xThird, yThird)
        )
        drawRect(rowColors[position.first],
            Offset(size.width, size.height / 2 - colorSize),
            Size(xThird, colorSize * 2)
        )
    }
    if (position.second != -1) {
        drawRect(greenWhite,
            Offset(xThird, size.height),
            Size(xThird, yThird)
        )
        drawRect(
            rowColors[position.second],
            Offset(size.width / 2 - colorSize, size.height),
            Size(colorSize * 2, yThird)
        )
    }
}

@Preview
@Composable
private fun TilePreview() {
    VoltorbFlipTheme {
        Column {
            Tile(Screen.IN_GAME, 0 to 0, TileState.STATIC_INFO) {}
            Tile(Screen.IN_GAME, 0 to 1, TileState.NOT_FLIPPED) {}
            Tile(Screen.IN_GAME, 0 to 1, TileState.NOT_FLIPPED, isInFocus = true) {}
            Tile(Screen.IN_GAME, 0 to 4, TileState.NOT_FLIPPED, isInFocus = true, isMemoOpen = true) {}
            Tile(Screen.IN_GAME, 0 to 2, TileState.FLIPPED) {}
            Tile(Screen.IN_GAME, 0 to 3, TileState.FLIPPED, isInFocus = true) {}
            Tile(Screen.IN_GAME, 0 to 4, TileState.FLIPPED, isInFocus = true, isMemoOpen = true) {}
        }
    }
}

@Preview
@Composable
private fun TileConnectorPreview() {
    VoltorbFlipTheme {
        Column {
            Row {
                FlipTile(Screen.IN_GAME, 3 to 3, 0)
                FlipTile(Screen.IN_GAME, 3 to 4, 1, isFlipped = true)
                InfoTile(3, 4, 1)
            }
            Row {
                FlipTile(Screen.IN_GAME, 4 to 3, 0)
                FlipTile(Screen.IN_GAME, 4 to 4, 0)
                InfoTile(4, 8, 1)
            }
            Row {
                InfoTile(3, 4, 2)
                InfoTile(4, 6, 1)
            }
        }
    }
}