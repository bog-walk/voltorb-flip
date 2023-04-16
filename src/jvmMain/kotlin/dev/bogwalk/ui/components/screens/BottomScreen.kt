package dev.bogwalk.ui.components.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.style.*

@Composable
fun BottomScreen(
    modifier: Modifier,
    content: @Composable() (BoxScope.() -> Unit)
) {
    Box(
        modifier = modifier
            .background(darkGreen)
            .drawBehind {
                drawRoundRect(
                    lightGreen,
                    Offset(standardPadding.toPx(), standardPadding.toPx()),
                    Size(size.width - (standardPadding * 2).toPx(),
                        size.height - (standardPadding * 2).toPx()),
                    CornerRadius(5.dp.toPx())
                )
            },
        content = content
    )
}

@Preview
@Composable
private fun BottomScreenPreview() {
    VoltorbFlipTheme {
        Box(Modifier.requiredSize(windowWidth / 2)) {
            BottomScreen(Modifier.fillMaxSize()) {}
        }
    }
}