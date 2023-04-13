package dev.bogwalk.ui.components.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.components.tiles.FlipTile
import dev.bogwalk.ui.components.buttons.QuitButton
import dev.bogwalk.ui.style.*

@Composable
fun HowToPlayScreen() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FlipTile(Screen.ABOUT_GAME, -1 to -1, 3, isFlipped = true)
        Icon(
            painter = painterResource(INFO_ARROW),
            contentDescription = INFO_ARROW_DESCR,
            modifier = Modifier.requiredSize(26.dp),
            tint = Color.Unspecified
        )
        FlipTile(Screen.ABOUT_GAME, -1 to -1, 1, isFlipped = true)
        Icon(
            painter = painterResource(INFO_ARROW),
            contentDescription = INFO_ARROW_DESCR,
            modifier = Modifier.requiredSize(26.dp),
            tint = Color.Unspecified
        )
        FlipTile(Screen.ABOUT_GAME, -1 to -1, 2, isFlipped = true)
        Icon(
            painter = painterResource(INFO_ARROW),
            contentDescription = INFO_ARROW_DESCR,
            modifier = Modifier.requiredSize(26.dp),
            tint = Color.Unspecified
        )
        FlipTile(Screen.ABOUT_GAME, -1 to -1, 1, isFlipped = true)
        Icon(
            painter = painterResource(INFO_ARROW),
            contentDescription = INFO_ARROW_DESCR,
            modifier = Modifier.requiredSize(26.dp),
            tint = Color.Unspecified
        )
        FlipTile(Screen.ABOUT_GAME, -1 to -1, 3, isFlipped = true)
    }
    InfoTextBox(
        Modifier.fillMaxWidth()
    ) {
        Text(
            text = HOW_TO_INFO_1,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
    Icon(
        painter = painterResource(INFO_ARROW),
        contentDescription = INFO_ARROW_DESCR,
        modifier = Modifier
            .requiredSize(26.dp)
            .rotate(90f),
        tint = Color.Unspecified
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(.25f),
            contentAlignment = Alignment.Center
        ) {
            QuitButton(Screen.ABOUT_GAME, 0 to 0, false)
        }
        InfoTextBox(
            Modifier.weight(.75f)
        ) {
            Text(
                text = HOW_TO_INFO_2,
                modifier = Modifier.padding(vertical = 10.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(.25f),
            contentAlignment = Alignment.Center
        ) {
            FlipTile(Screen.ABOUT_GAME, -1 to -1, 0, isFlipped = true)
        }
        InfoTextBox(
            Modifier.weight(.75f)
        ) {
            Text(
                text = HOW_TO_INFO_3,
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
        Box(Modifier.requiredSize(450.dp, 360.dp)) {
            TopScreen(Modifier) { HowToPlayScreen() }
        }
    }
}