package dev.bogwalk.ui.components.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.components.tiles.FlipTile
import dev.bogwalk.ui.components.buttons.QuitButton
import dev.bogwalk.ui.style.VoltorbFlipTheme

@Composable
fun HowToPlayScreen() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FlipTile(-1 to -1, 3, isFlipped = true)
        Icon(
            painter = painterResource("info_arrow.svg"),
            contentDescription = null,
            modifier = Modifier.requiredSize(30.dp))
        FlipTile(-1 to -1, 1, isFlipped = true)
        Icon(
            painter = painterResource("info_arrow.svg"),
            contentDescription = null,
            modifier = Modifier.requiredSize(30.dp))
        FlipTile(-1 to -1, 2, isFlipped = true)
        Icon(
            painter = painterResource("info_arrow.svg"),
            contentDescription = null,
            modifier = Modifier.requiredSize(30.dp))
        FlipTile(-1 to -1, 1, isFlipped = true)
        Icon(
            painter = painterResource("info_arrow.svg"),
            contentDescription = null,
            modifier = Modifier.requiredSize(30.dp))
        FlipTile(-1 to -1, 3, isFlipped = true)
    }
    InfoTextBox(
        Modifier.fillMaxWidth()
    ) {
        Text(
            text = "If you flip the cards in this order,\n" +
                    "you'll collect: 3 x 1 x 2 x 1 x 3...\n" +
                    "A total of 18 Coins! And then...",
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
    Icon(
        painter = painterResource("info_arrow.svg"),
        contentDescription = null,
        modifier = Modifier.requiredSize(30.dp))
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(.3f),
            contentAlignment = Alignment.Center
        ) {
            QuitButton(-2 to -2, false)
        }
        InfoTextBox(
            Modifier.weight(.7f)
        ) {
            Text(
                text = "If you select \"Quit,\"\nyou'll keep those 18 Coins.",
                modifier = Modifier.padding(vertical = 10.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(.3f),
            contentAlignment = Alignment.Center
        ) {
            FlipTile(-1 to -1, 0, isFlipped = true)
        }
        InfoTextBox(
            Modifier.weight(.7f)
        ) {
            Text(
                text = "But if you find VOLTORB\nyou'll lose all your Coins!",
                modifier = Modifier.padding(vertical = 10.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
private fun HowToPlayScreenPreview() {
    VoltorbFlipTheme {
        Box(Modifier.requiredSize(450.dp)) {
            TopScreen(Modifier) { HowToPlayScreen() }
        }
    }
}