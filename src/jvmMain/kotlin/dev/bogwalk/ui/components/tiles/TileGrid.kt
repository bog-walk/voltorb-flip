package dev.bogwalk.ui.components.tiles

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.bogwalk.model.GameTile
import dev.bogwalk.ui.style.VoltorbFlipTheme

@Composable
fun TileGrid(
    grid: List<List<GameTile>>,
    infoGrid: List<Pair<Int, Int>>,
    currentPosition: Pair<Int, Int>,
    isMemoOpen: Boolean,
    onSelectRequest: (Pair<Int, Int>) -> Unit
) {
    Column(
        Modifier.padding(start = 8.dp, top = 8.dp, end = 4.dp, bottom = 8.dp)
    ) {
        for ((i, row) in grid.withIndex()) {
            Row {
                for (tile in row) {
                    key(tile.position) {
                        FlipTile(
                            tile.position, tile.value, tile.memoData,
                            isInFocus = tile.position == currentPosition,
                            isFlipped = tile.isFlipped,
                            isMemoOpen = isMemoOpen,
                            onSelectRequest = onSelectRequest
                        )
                    }
                }
                InfoTile(i, infoGrid[i].first, infoGrid[i].second)
            }
        }
        Row {
            for (j in 5..9) {
                key("$4,${j % 5}") {
                    InfoTile(j % 5, infoGrid[j].first, infoGrid[j].second)
                }
            }
        }
    }
}

@Preview
@Composable
private fun TileGridPreview() {
    VoltorbFlipTheme {
        TileGrid(
            grid = List(5) { r -> List(5) { c -> GameTile(r to c, 1) } },
            infoGrid = listOf(
                7 to 0, 6 to 0, 6 to 1, 3 to 2, 2 to 3, 5 to 1, 6 to 1, 2 to 3, 6 to 0, 5 to 1
            ),
            currentPosition = 0 to 0,
            isMemoOpen = false,
            onSelectRequest = {}
        )
    }
}