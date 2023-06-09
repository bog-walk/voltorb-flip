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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.Screen
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
    CoinsBox(PLAYER_COINS, totalCoins)
    CoinsBox(CURRENT_COINS, currentCoins)
}

@Composable
private fun LevelHeader(level: Int) {
    Text(
        text = "$HEADER_START$level$HEADER_END",
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .background(darkGreen)
            .drawBehind {
                drawLine(darkGrey,
                    Offset.Zero,
                    Offset(size.width, 0f),
                    thinBorder.toPx())
                drawLine(darkGrey,
                    Offset(0f, size.height),
                    Offset(size.width, size.height),
                    thinBorder.toPx())
                drawLine(offWhite,
                    Offset(0f, 3.1.dp.toPx()),
                    Offset(size.width, 3.1.dp.toPx()),
                    thickBorder.toPx())
                drawLine(offWhite,
                    Offset(0f, size.height - 3.1.dp.toPx()),
                    Offset(size.width, size.height - 3.1.dp.toPx()),
                    thickBorder.toPx())
            }
            .padding(vertical = 8.dp),
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
private fun RulesBox() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = standardPadding, vertical = thickBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind { drawUnderLine() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            FlipTile(Screen.IN_GAME, -1 to -1, 1, isFlipped = true)
            FlipTile(Screen.IN_GAME, -1 to -1, 2, isFlipped = true)
            FlipTile(Screen.IN_GAME, -1 to -1, 3, isFlipped = true)
            Text(
                text = INFO_POINTS,
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
            FlipTile(Screen.IN_GAME, -1 to -1, 0, isFlipped = true)
            Text(
                text = GAME_OVER,
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
        thickBorder.toPx()
    )
}

@Composable
private fun CoinsBox(
    text: String,
    coins: Int
) {
    InfoTextBox(
        Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .testTag(COIN_TAG)
                .fillMaxWidth()
                .padding(horizontal = thickerPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                modifier = Modifier.weight(.6f).padding(vertical = thickerPadding),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            // technically this should be more pixelated and should carry a rolling animation
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
        TopScreen(Modifier.requiredSize(windowWidth / 2, windowHeight)) {
            InfoScreen(1, 0, 0)
        }
    }
}