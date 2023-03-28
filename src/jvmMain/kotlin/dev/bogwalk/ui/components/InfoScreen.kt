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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.InsetPattern
import dev.bogwalk.ui.util.drawBorder

@Composable
fun InfoScreen(
    level: Int,
    totalCoins: Int,
    currentCoins: Int
) {
    Column(Modifier
        .fillMaxWidth()
        .padding(bottom = 5.dp)
    ) {
        LevelHeader(level)
        RulesBox()
        CoinTextBox("Player's\nCollected Coins", totalCoins)
        CoinTextBox("Coins Collected in\nCurrent Game", currentCoins)
    }
}

@Composable
private fun LevelHeader(level: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(darkGreen)
            .drawWithContent {  // are these colours correct?
                drawContent()
                drawLine(darkGrey,
                    Offset(0f, (42.dp / 3).toPx()),
                    Offset(size.width, (42.dp / 3).toPx()),
                    2.dp.toPx())
                drawLine(darkGrey,
                    Offset(0f, size.height),
                    Offset(size.width, size.height),
                    2.dp.toPx())
                drawLine(offWhite,
                    Offset(0f, (3.1.dp + 42.dp / 3).toPx()),
                    Offset(size.width, (3.1.dp + 42.dp / 3).toPx()),
                    4.dp.toPx())
                drawLine(offWhite,
                    Offset(0f, size.height - 3.1.dp.toPx()),
                    Offset(size.width, size.height - 3.1.dp.toPx()),
                    4.dp.toPx())
            }
    ) {
        InsetPattern(1)
        Text(
            text = "VOLTORB Flip Lv. $level\nFlip the Cards and Collect Coins!",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 20.dp, bottom = 7.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun RulesBox() {
    Column(
        modifier = Modifier
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
private fun CoinTextBox(
    text: String,
    coins: Int
) {
    Row(
        modifier = Modifier
            .requiredHeight(70.dp)
            .fillMaxWidth()
            .padding(horizontal = 7.dp, vertical = 3.dp)
            .background(Color.White)
            .drawBehind {
                drawBorder(2.dp.toPx(), 0f, darkGrey, StrokeCap.Butt)
                drawBorder(4.dp.toPx(), 3.1.dp.toPx(), greenWhite2)
                        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = Modifier.weight(.6f),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = coins.toString().padStart(5, '0'),
            modifier = Modifier.padding(end = 7.dp),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview
@Composable
private fun InfoScreenPreview() {
    VoltorbFlipTheme {
        Box(Modifier.requiredWidth(445.dp)) {
            InfoScreen(1, 0, 0)
        }
    }
}