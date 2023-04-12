package dev.bogwalk.ui.components.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.components.tiles.FlipTile
import dev.bogwalk.ui.components.tiles.InfoTile
import dev.bogwalk.ui.style.*

@Composable
fun HintScreen() {
    Row(
        modifier = Modifier.padding(top = 40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        FlipTile(0 to -1, 3)
        FlipTile(0 to -1, 0)
        FlipTile(0 to -1, 3)
        FlipTile(0 to -1, 0)
        FlipTile(0 to -1, 1)
        InfoTile(0, 7, 2)
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
        Modifier
            .padding(vertical = 15.dp)
            .drawWithContent {
                drawContent()
                drawOutlinedLine(isOutline = true)
                drawOutlinedLine(isOutline = false)
            }
    ) {
        FlipTile(0 to -1, 3, isFlipped = true)
        FlipTile(0 to -1, 0, isFlipped = true)
        FlipTile(0 to -1, 3, isFlipped = true)
        FlipTile(0 to -1, 0, isFlipped = true)
        FlipTile(0 to -1, 1, isFlipped = true)
        InfoTile(0, 7, 2)
    }
    InfoTextBox(
        Modifier.fillMaxWidth()
    ) {
        Text(
            text = HINT_INFO,
            modifier = Modifier.padding(vertical = 10.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// a better way to draw a single line with different fill vs stroke?
// there should also technically be arrows at the rightmost terminus...
private fun DrawScope.drawOutlinedLine(isOutline: Boolean) {
    val depth = 10.dp.toPx()
    val outer = 6.dp.toPx()
    val stroke = if (isOutline) outer else outer / 2
    val color = if (isOutline) greenWhite else darkGrey

    drawLine(color, Offset(size.width / 12, -(depth + outer)),
        Offset(size.width / 12, depth),
        stroke, StrokeCap.Round)
    drawLine(color, Offset(size.width / 12 * 5, -(depth + outer)),
        Offset(size.width / 12 * 5, depth),
        stroke, StrokeCap.Round)
    drawLine(color, Offset(size.width / 4 * 3, -(depth + outer)),
        Offset(size.width / 4 * 3, depth),
        stroke, StrokeCap.Round)
    drawLine(color, Offset(size.width / 12, -(depth + outer)),
        Offset(size.width / 12 * 11, -(depth + outer)),
        stroke, StrokeCap.Round)
    drawLine(color, Offset(size.width / 12 * 11, -(depth + outer)),
        Offset(size.width / 12 * 11, depth - outer),
        stroke, StrokeCap.Round)

    drawLine(color, Offset(size.width / 4, size.height - depth),
        Offset(size.width / 4, size.height + depth + outer),
        stroke, StrokeCap.Round)
    drawLine(color, Offset(size.width / 12 * 7, size.height - depth),
        Offset(size.width / 12 * 7, size.height + depth + outer),
        stroke, StrokeCap.Round)
    drawLine(color, Offset(size.width / 4, size.height + depth + outer),
        Offset(size.width / 12 * 11, size.height + depth + outer),
        stroke, StrokeCap.Round)
    drawLine(color, Offset(size.width / 12 * 11, size.height - depth + outer),
        Offset(size.width / 12 * 11, size.height + depth + outer),
        stroke, StrokeCap.Round)
}

@Preview
@Composable
private fun HintScreenPreview() {
    VoltorbFlipTheme {
        Box(Modifier.requiredSize(450.dp, 360.dp)) {
            TopScreen(Modifier) { HintScreen() }
        }
    }
}