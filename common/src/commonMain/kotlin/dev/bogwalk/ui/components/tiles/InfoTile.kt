package dev.bogwalk.ui.components.tiles

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.bogwalk.common.generated.resources.Res
import dev.bogwalk.common.generated.resources.zero
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.style.INFO_ZERO_DESCR
import dev.bogwalk.ui.style.VoltorbFlipTheme
import dev.bogwalk.ui.style.zeroIconSize
import org.jetbrains.compose.resources.painterResource

@Composable
fun InfoTile(
    rowIndex: Int,
    points: Int,
    mines: Int
) {
    Tile(
        screen = Screen.IN_GAME,
        position = rowIndex to -1,
        tileState = TileState.STATIC_INFO
    ) {
        Text(
            text = points.toString().padStart(2, '0'),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-2).dp, y = (-3).dp),
            style = MaterialTheme.typography.labelSmall
        )
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .padding(start = 2.dp, end = 3.dp, bottom = 1.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(Res.drawable.zero),
                contentDescription = INFO_ZERO_DESCR,
                modifier = Modifier.requiredSize(zeroIconSize),
                tint = Color.Unspecified
            )
            Text(
                text = mines.toString(),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Preview
@Composable
private fun InfoTilePreview() {
    VoltorbFlipTheme {
        Column {
            InfoTile(0, 10, 1)
            InfoTile(1, 2, 3)
            InfoTile(2, 3, 2)
            InfoTile(3, 4, 2)
            InfoTile(4, 8, 1)
        }
    }
}