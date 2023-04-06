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
import dev.bogwalk.ui.style.*

@Composable
fun HowToPlayScreen() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FlipTile(-1 to -1, 3, isFlipped = true)
        Icon(
            painter = painterResource(INFO_ARROW),
            contentDescription = INFO_ARROW_DESCR,
            modifier = Modifier.requiredSize(30.dp))
        FlipTile(-1 to -1, 1, isFlipped = true)
        Icon(
            painter = painterResource(INFO_ARROW),
            contentDescription = INFO_ARROW_DESCR,
            modifier = Modifier.requiredSize(30.dp))
        FlipTile(-1 to -1, 2, isFlipped = true)
        Icon(
            painter = painterResource(INFO_ARROW),
            contentDescription = INFO_ARROW_DESCR,
            modifier = Modifier.requiredSize(30.dp))
        FlipTile(-1 to -1, 1, isFlipped = true)
        Icon(
            painter = painterResource(INFO_ARROW),
            contentDescription = INFO_ARROW_DESCR,
            modifier = Modifier.requiredSize(30.dp))
        FlipTile(-1 to -1, 3, isFlipped = true)
    }
    InfoTextBox(
        Modifier.fillMaxWidth()
    ) {
        Text(
            text = HOW_TO_TEXT_1,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
    Icon(
        painter = painterResource(INFO_ARROW),
        contentDescription = INFO_ARROW_DESCR,
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
                text = HOW_TO_TEXT_2,
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
                text = HOW_TO_TEXT_3,
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