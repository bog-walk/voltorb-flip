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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.bogwalk.ui.style.FLIPPED_DESCR
import dev.bogwalk.ui.style.THANKS
import dev.bogwalk.ui.style.VoltorbFlipTheme

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
        painter = painterResource("pixel_0.svg"),
        contentDescription = "$FLIPPED_DESCR$0",
        modifier = Modifier
            .fillMaxSize()
            .requiredSize(69.dp)
            .wrapContentSize(Alignment.Center),
        tint = Color.Unspecified
    )
}

@Preview
@Composable
private fun ThanksTopScreenPreview() {
    VoltorbFlipTheme {
        TopScreen(Modifier.requiredSize(450.dp, 360.dp)) {
            ThanksTopScreen()
        }
    }
}

@Preview
@Composable
private fun ThanksBottomScreenPreview() {
    VoltorbFlipTheme {
        BottomScreen(Modifier.requiredSize(450.dp, 360.dp)) {
            ThanksBottomScreen()
        }
    }
}