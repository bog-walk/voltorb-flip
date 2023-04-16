package dev.bogwalk.ui.components.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.style.*
import dev.bogwalk.ui.util.drawBorder

@Composable
fun TopScreen(
    modifier: Modifier,
    content: @Composable() (ColumnScope.() -> Unit)
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(lightGreen)
            .drawBehind {
                val top = standardPadding.toPx()
                val patternSize = (tileSize / 3).toPx()
                drawRect(darkGreen, Offset.Zero, Size(size.width, top))

                var yStart = top
                var alpha = 1f
                for (i in 1..4) {
                    var xStart = if (i % 2 == 0) patternSize else 0f
                    while (xStart < size.width) {
                        drawRect(
                            darkGreen,
                            Offset(xStart, yStart),
                            Size(patternSize, patternSize),
                            alpha
                        )
                        xStart += patternSize * 2
                    }
                    yStart += patternSize
                    alpha -= .25f
                }
            },
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}

@Composable
fun InfoTextBox(
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.() -> Unit)
) {
    Box(
        modifier
            .padding(start = 7.dp, end = 7.dp, bottom = 7.dp)
            .background(Color.White)
            .drawBehind {
                drawBorder(thinBorder.toPx(), 0f, darkGrey, StrokeCap.Butt)
                drawBorder(thickBorder.toPx(), 3.1.dp.toPx(), greenWhite2, StrokeCap.Square)
            },
        contentAlignment = Alignment.Center,
        content = content
    )
}

@Preview
@Composable
private fun TopScreenPreview() {
    VoltorbFlipTheme {
        Box(Modifier.requiredSize(windowWidth / 2)) {
            TopScreen(Modifier) {}
        }
    }
}

@Preview
@Composable
private fun InfoTextBoxPreview() {
    VoltorbFlipTheme {
        InfoTextBox(Modifier.fillMaxWidth().requiredHeight(60.dp).padding(10.dp)) {}
    }
}