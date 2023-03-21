package dev.bogwalk.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.style.VoltorbFlipTheme

@Composable
fun InfoTile(
    points: Int,
    mines: Int,
    rowIndex: Int
) {
    Tile(rowIndex, 0, TileState.STATIC_INFO) {
        Text(
            points.toString().padStart(2, '0'),
            modifier = Modifier.align(Alignment.TopEnd).offset(y = (-2).dp).padding(end=3.dp),
            style = MaterialTheme.typography.labelMedium
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart).fillMaxWidth()
                .padding(start=4.dp, end=4.dp, bottom=3.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource("voltorb.svg"),
                contentDescription = "Voltorb",
                modifier = Modifier.requiredSize(29.dp),
                tint = Color.Unspecified
            )
            Text(
                mines.toString(),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview
@Composable
private fun InfoTilePreview() {
    VoltorbFlipTheme {
        Column {
            InfoTile(5, 1, 0)
            InfoTile(3, 2, 1)
            InfoTile(5, 3, 2)
            InfoTile(4, 1, 3)
            InfoTile(8, 1, 4)
        }
    }
}