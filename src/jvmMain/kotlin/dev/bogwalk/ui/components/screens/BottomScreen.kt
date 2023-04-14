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
import dev.bogwalk.ui.style.VoltorbFlipTheme
import dev.bogwalk.ui.style.darkGreen
import dev.bogwalk.ui.style.lightGreen

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
                    Offset(6.dp.toPx(), 6.dp.toPx()),
                    Size(size.width - 12.dp.toPx(), size.height - 12.dp.toPx()),
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
        Box(Modifier.requiredSize(450.dp)) {
            BottomScreen(Modifier.fillMaxSize()) {}
        }
    }
}