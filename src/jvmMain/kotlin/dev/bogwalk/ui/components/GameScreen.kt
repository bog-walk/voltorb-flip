package dev.bogwalk.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import dev.bogwalk.model.GameTile
import dev.bogwalk.ui.style.VoltorbFlipTheme
import dev.bogwalk.ui.style.darkGreen
import dev.bogwalk.ui.style.lightGreen

@Composable
fun GameScreen(
    grid: List<List<GameTile>>,
    infoGrid: List<Pair<Int, Int>>,
    currentPosition: Pair<Int, Int>,
    isMemoOpen: Boolean,
    onSelectRequest: (Pair<Int, Int>) -> Unit,
    onMemoRequest: () -> Unit,
    onEditRequest: (Int) -> Unit,
    onQuitRequest: () -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(darkGreen)
            .drawBehind {
                drawRoundRect(lightGreen,
                    Offset(5.dp.toPx(), 5.dp.toPx()),
                    Size(size.width - 10.dp.toPx(), size.height - 10.dp.toPx()),
                    CornerRadius(5.dp.toPx())
                )
            }
    ) {
        Row(
            modifier = Modifier
                .padding(6.dp)
                .height(IntrinsicSize.Max)
        ) {
            TileGrid(grid, infoGrid, currentPosition, isMemoOpen, onSelectRequest)
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MemoButton(isMemoOpen, onMemoRequest)
                if (isMemoOpen) {
                    MemoPad(grid[0][0].memoData, onEditRequest, onQuitRequest)
                }
                QuitButton(currentPosition, isMemoOpen, onQuitRequest)
            }
        }
    }
}

@Preview
@Composable
private fun GameScreenPreview() {
    VoltorbFlipTheme {
        Box(Modifier.requiredWidth(445.dp)) {
            GameScreen(
                List(5) { r -> List(5) { c -> GameTile(r to c, 1) } },
                listOf(7 to 0, 6 to 0, 6 to 1, 3 to 2, 2 to 3, 5 to 1, 6 to 1, 2 to 3, 6 to 0, 5 to 1),
                0 to 0, false,
                onSelectRequest = {}, onEditRequest = {}, onMemoRequest = {}
            ) {}
        }
    }
}

@Preview
@Composable
private fun GameScreenWithMemoPreview() {
    VoltorbFlipTheme {
        Box(Modifier.requiredWidth(445.dp)) {
            GameScreen(
                List(5) { r -> List(5) { c -> GameTile(r to c, 1) } },
                listOf(7 to 0, 6 to 0, 6 to 1, 3 to 2, 2 to 3, 5 to 1, 6 to 1, 2 to 3, 6 to 0, 5 to 1),
                2 to 1, true,
                onSelectRequest = {}, onEditRequest = {}, onMemoRequest = {}
            ) {}
        }
    }
}