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
import dev.bogwalk.ui.components.buttons.MemoButton
import dev.bogwalk.ui.components.buttons.MemoPad
import dev.bogwalk.ui.components.tiles.FlipTile
import dev.bogwalk.ui.style.ABOUT_TEXT
import dev.bogwalk.ui.style.INFO_ARROW
import dev.bogwalk.ui.style.INFO_ARROW_DESCR
import dev.bogwalk.ui.style.VoltorbFlipTheme

@Composable
fun AboutMemoScreen() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MemoButton(isMemoOpen = false, inGameUse = false)
        Icon(
            painter = painterResource(INFO_ARROW),
            contentDescription = INFO_ARROW_DESCR,
            modifier = Modifier.requiredSize(30.dp))
        MemoPad(booleanArrayOf(true, true, true, false), inGameUse = false)
        Icon(
            painter = painterResource(INFO_ARROW),
            contentDescription = INFO_ARROW_DESCR,
            modifier = Modifier.requiredSize(30.dp))
        FlipTile(-1 to -1, 1, booleanArrayOf(true, true, true, false),
            isMemoOpen = true) {}
    }
    InfoTextBox(
        Modifier.fillMaxWidth()
    ) {
        Text(
            text = ABOUT_TEXT,
            modifier = Modifier.padding(vertical = 10.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
private fun AboutMemoScreenPreview() {
    VoltorbFlipTheme {
        Box(Modifier.requiredSize(450.dp)) {
            TopScreen(Modifier) { AboutMemoScreen() }
        }
    }
}