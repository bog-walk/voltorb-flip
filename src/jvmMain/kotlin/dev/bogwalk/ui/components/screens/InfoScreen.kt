package dev.bogwalk.ui.components.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.components.tiles.FlipTile
import dev.bogwalk.ui.style.*

@Composable
fun InfoScreen(
    level: Int,
    totalCoins: Int,
    currentCoins: Int
) {
    LevelHeader(level)
    RulesBox()
    CoinsBox("Player's\nCollected Coins", totalCoins)
    CoinsBox("Coins Collected in\nCurrent Game", currentCoins)
}

@Composable
private fun LevelHeader(level: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .background(darkGreen)
            .drawWithContent {  // are these colours correct?
                drawContent()
                drawLine(darkGrey,
                    Offset.Zero,
                    Offset(size.width, 0f),
                    2.dp.toPx())
                drawLine(darkGrey,
                    Offset(0f, size.height),
                    Offset(size.width, size.height),
                    2.dp.toPx())
                drawLine(offWhite,
                    Offset(0f, 3.1.dp.toPx()),
                    Offset(size.width, 3.1.dp.toPx()),
                    4.dp.toPx())
                drawLine(offWhite,
                    Offset(0f, size.height - 3.1.dp.toPx()),
                    Offset(size.width, size.height - 3.1.dp.toPx()),
                    4.dp.toPx())
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "VOLTORB Flip Lv. $level\nFlip the Cards and Collect Coins!",
            modifier = Modifier.padding(vertical = 8.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun RulesBox() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind { drawUnderLine() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            FlipTile(-1 to -1, 1, isFlipped = true)
            FlipTile(-1 to -1, 2, isFlipped = true)
            FlipTile(-1 to -1, 3, isFlipped = true)
            Text(
                text = "...x1! ...x2! ...x3!",
                modifier = Modifier.weight(.6f).padding(start = 5.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 112.dp)
                .drawBehind { drawUnderLine() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            FlipTile(-1 to -1, 0, isFlipped = true)
            Text(
                text = "Game Over! 0!",
                modifier = Modifier.padding(start = 5.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

private fun DrawScope.drawUnderLine() {
    drawLine(
        greenWhite,
        Offset(7.dp.toPx(), size.height - 7.dp.toPx()),
        Offset(size.width - 7.dp.toPx(), size.height - 7.dp.toPx()),
        4.dp.toPx()
    )
}

@Composable
private fun CoinsBox(
    text: String,
    coins: Int? = null
) {
    InfoTextBox(
        Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .requiredHeight(65.dp)
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                modifier = Modifier.weight(.6f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = coins.toString().padStart(5, '0'),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview
@Composable
private fun InfoScreenPreview() {
    VoltorbFlipTheme {
        Box(Modifier.requiredSize(450.dp)) {
            TopScreen(Modifier) { InfoScreen(1, 0, 0) }
        }
    }
}