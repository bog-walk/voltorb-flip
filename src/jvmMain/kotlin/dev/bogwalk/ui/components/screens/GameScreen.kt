package dev.bogwalk.ui.components.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.bogwalk.model.GameTile
import dev.bogwalk.ui.components.buttons.MemoButton
import dev.bogwalk.ui.components.buttons.MemoPad
import dev.bogwalk.ui.components.buttons.QuitButton
import dev.bogwalk.ui.components.tiles.TileGrid
import dev.bogwalk.ui.style.VoltorbFlipTheme
import dev.bogwalk.ui.style.darkGreen
import dev.bogwalk.ui.style.darkGrey
import dev.bogwalk.ui.style.lightGreen
import dev.bogwalk.ui.util.drawLineBorder

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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .background(darkGreen)
            .drawBehind {
                drawRoundRect(
                    lightGreen,
                    Offset(5.dp.toPx(), 5.dp.toPx()),
                    Size(size.width - 10.dp.toPx(), size.height - 10.dp.toPx()),
                    CornerRadius(5.dp.toPx())
                )
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TileGrid(grid, infoGrid, currentPosition, isMemoOpen, onSelectRequest)
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MemoButton(isMemoOpen, onSelectRequest = onMemoRequest)
            if (isMemoOpen) {
                MemoPad(grid[0][0].memoData, onEditRequest = onEditRequest,
                    onQuitRequest = onQuitRequest)
            }
            QuitButton(currentPosition, isMemoOpen, onQuitRequest)
        }
    }
}

@Composable
fun SpeechBox(
    text: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(horizontal = 5.dp, vertical = 10.dp)
            .background(Brush.verticalGradient(
                .7f to Color(0xff63636b),
                .8f to Color(0xff5a5a63),
                .85f to Color(0xff52525a))
            )
            .drawBehind {
                // are these colours correct?
                drawLineBorder(outerColor = darkGrey)
                drawLineBorder(innerColor = Color(0xffffde6b))
            },
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth(.9f)
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .background(Color.White, RoundedCornerShape(2.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
            ,
            maxLines = 2,
            style = MaterialTheme.typography.bodyMedium
        )
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

@Preview
@Composable
private fun SpeechBoxPreview() {
    VoltorbFlipTheme {
        Column {
            SpeechBox("Play VOLTORB Flip Lv. 1?\n")
            SpeechBox("Here is an example of some long text that should break")
        }
    }
}