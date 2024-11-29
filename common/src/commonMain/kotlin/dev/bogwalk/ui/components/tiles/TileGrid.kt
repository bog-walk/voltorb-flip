package dev.bogwalk.ui.components.tiles

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import dev.bogwalk.model.GameTile
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.style.GRID_TAG
import dev.bogwalk.ui.style.VoltorbFlipTheme

@Composable
fun TileGrid(
    screen: Screen,
    grid: List<GameTile>,
    infoGrid: List<Pair<Int, Int>>,
    currentPosition: Pair<Int, Int>,
    isMemoOpen: Boolean,
    onSelectRequest: (Pair<Int, Int>) -> Unit
) {
    Column(
        Modifier
            .padding(start = 8.dp, top = 8.dp, end = 4.dp, bottom = 8.dp)
            .testTag(GRID_TAG)
    ) {
        for (row in 0..4) {
            Row {
                for (col in 0..4) {
                    key("$row,$col") {
                        val tile = grid[row * 5 + col]
                        FlipTile(
                            screen,
                            tile.position, tile.value, tile.memoData,
                            isInFocus = tile.position == currentPosition,
                            tile.isFlipped,
                            isMemoOpen,
                            onSelectRequest
                        )
                    }
                }
                InfoTile(row, infoGrid[row].first, infoGrid[row].second)
            }
        }
        Row {
            for (j in 5..9) {
                key("$5,${j % 5}") {
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
            screen = Screen.IN_GAME,
            grid = List(25) { r ->
                List(5) { c -> GameTile(r to c, 1) }
            }.flatten(),
            infoGrid = listOf(
                7 to 0, 6 to 0, 6 to 1, 3 to 2, 2 to 3, 5 to 1, 6 to 1, 2 to 3, 6 to 0, 5 to 1
            ),
            currentPosition = 0 to 0,
            isMemoOpen = false) {}
    }
}