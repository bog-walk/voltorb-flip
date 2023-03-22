package dev.bogwalk.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.drawBorder

@Composable
fun InfoScreen(
    level: Int,
    totalCoins: Int,
    currentCoins: Int
) {
    Column(
        Modifier.requiredWidth(600.dp)
    ) {
        LevelHeader(level)
        RulesBox()
        CoinTextBox("Player's\nCollected Coins", totalCoins)
        CoinTextBox("Coins Collected in Current Game", currentCoins)
    }
}

@Composable
private fun LevelHeader(level: Int) {
    Box(
        modifier = Modifier
            .requiredHeight(110.dp)
            .background(darkGreen)
            .drawBehind {  // are these colours correct?
                drawPattern()
                drawLine(darkGrey,
                    Offset(0f, 18f),
                    Offset(size.width, 18f), 3f)
                drawLine(offWhite,
                    Offset(0f, 22f),
                    Offset(size.width, 22f), 6.6f)
                drawLine(offWhite,
                    Offset(0f, size.height - 5f),
                    Offset(size.width, size.height - 5f), 6.6f)
                drawLine(darkGrey,
                    Offset(0f, size.height),
                    Offset(size.width, size.height), 3f)
            }
    ) {
        Text(
            "VOLTORB Flip Lv. $level\nFlip the Cards and Collect Coins!",
            Modifier.fillMaxWidth().padding(top=35.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

private fun DrawScope.drawPattern() {
    var start = 25f
    while (start < size.width) {
        drawRect(lightGreen,
            Offset(start, 8f),
            Size(15f, 10f))

        start += 15f + 25f
    }
}

@Composable
private fun RulesBox() {
    Box(
        modifier = Modifier
            .requiredHeight(150.dp)
            .padding(horizontal = 2.dp)
    ) {
        Row(
            Modifier.fillMaxWidth()
                .align(Alignment.TopStart)
                .drawBehind {
                    drawLine(greenWhite, Offset(8f, 66f), Offset(size.width - 8f, 66f), 6.6f)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            FlipTile(1, 0, 0, isFlipped = true, inGameGrid = false)
            FlipTile(2, 0, 0, isFlipped = true, inGameGrid = false)
            FlipTile(3, 0, 0, isFlipped = true, inGameGrid = false)
            Text("...x1! ...x2! ...x3!",
                modifier = Modifier.padding(start = 5.dp),
                style = MaterialTheme.typography.titleMedium)
        }
        // better way to create a baseline
        Row(
            Modifier.fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(start=152.dp)
                .drawBehind {
                    drawLine(greenWhite, Offset(8f, 66f), Offset(size.width - 8f, 66f), 6.6f)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            FlipTile(0, 0, 0, isFlipped = true, inGameGrid = false)
            Text("Game Over! 0!",
                modifier = Modifier.padding(start = 5.dp),
                style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun CoinTextBox(
    text: String,
    coins: Int
) {
    Box(
        modifier = Modifier
            .requiredHeight(80.dp)
            .fillMaxWidth().padding(5.dp)
            .background(Color.White)
            .drawBehind {
                drawBorder(3f, 0f, darkGrey)
                drawBorder(6.6f, 4f, greenWhite2)
            }
    ) {
        Text(text,
            modifier = Modifier.align(Alignment.CenterStart)
                .requiredWidth(320.dp).padding(start=100.dp),
            style = MaterialTheme.typography.bodyMedium)
        Text(coins.toString().padStart(5, '0'),
            modifier = Modifier.align(Alignment.CenterEnd).padding(end=15.dp),
            style = MaterialTheme.typography.titleLarge)
    }
}

@Preview
@Composable
private fun InfoScreenPreview() {
    VoltorbFlipTheme {
        InfoScreen(1, 0, 0)
    }
}