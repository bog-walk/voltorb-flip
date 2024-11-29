package dev.bogwalk.ui.components.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import dev.bogwalk.common.generated.resources.Res
import dev.bogwalk.common.generated.resources.pixel_0
import dev.bogwalk.ui.style.*
import org.jetbrains.compose.resources.painterResource

@Composable
fun ThanksTopScreen() {
    Text(
        text = THANKS,
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        style = MaterialTheme.typography.titleMedium.copy(fontSize = 30.sp)
    )
}

@Composable
fun ThanksBottomScreen() {
    Icon(
        painter = painterResource(Res.drawable.pixel_0),
        contentDescription = "${FLIPPED_DESCR}$0",
        modifier = Modifier
            .fillMaxSize()
            .requiredSize(tileSize * 2)
            .wrapContentSize(Alignment.Center),
        tint = Color.Unspecified
    )
}

@Preview
@Composable
private fun ThanksTopScreenPreview() {
    VoltorbFlipTheme {
        TopScreen(Modifier.requiredSize(windowWidth / 2, windowHeight)) {
            ThanksTopScreen()
        }
    }
}

@Preview
@Composable
private fun ThanksBottomScreenPreview() {
    VoltorbFlipTheme {
        BottomScreen(Modifier.requiredSize(windowWidth / 2, windowHeight)) {
            ThanksBottomScreen()
        }
    }
}