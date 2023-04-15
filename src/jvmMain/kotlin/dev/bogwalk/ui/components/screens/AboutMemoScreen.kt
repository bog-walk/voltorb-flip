package dev.bogwalk.ui.components.screens

import androidx.compose.animation.core.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.Screen
import dev.bogwalk.ui.components.buttons.MemoButton
import dev.bogwalk.ui.components.buttons.MemoPad
import dev.bogwalk.ui.components.tiles.FlipTile
import dev.bogwalk.ui.style.*

@Composable
fun AboutMemoScreen() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            MemoButton(Screen.ABOUT_MEMO, false)
            AnimatedStylus(Modifier.align(Alignment.CenterEnd), isReverse = false)
        }
        Icon(
            painter = painterResource(INFO_ARROW),
            contentDescription = INFO_ARROW_DESCR,
            modifier = Modifier.requiredSize(26.dp),
            tint = Color.Unspecified
        )
        Box {
            MemoPad(Screen.ABOUT_MEMO, listOf(true, true, true, false))
            AnimatedStylus(Modifier.align(Alignment.TopCenter), isReverse = true)
        }
        Icon(
            painter = painterResource(INFO_ARROW),
            contentDescription = INFO_ARROW_DESCR,
            modifier = Modifier.requiredSize(26.dp),
            tint = Color.Unspecified
        )
        FlipTile(Screen.ABOUT_MEMO, -1 to -1, 1,
            listOf(true, true, true, false),
            isMemoOpen = true) {}
    }
    InfoTextBox(
        Modifier.fillMaxWidth()
    ) {
        Text(
            text = ABOUT_INFO,
            modifier = Modifier.padding(vertical = 10.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun AnimatedStylus(
    modifier: Modifier,
    isReverse: Boolean
) {
    val limit = 20.dp
    val spec = infiniteRepeatable<Dp>(
        animation = tween(700, easing = FastOutLinearInEasing),
        repeatMode = RepeatMode.Reverse
    )
    val infiniteBounce = rememberInfiniteTransition()
    val xOffset by infiniteBounce.animateValue(
        initialValue = if (isReverse) limit else 0.dp,
        targetValue = if (isReverse) 0.dp else limit,
        typeConverter = Dp.VectorConverter,
        animationSpec = spec
    )
    val yOffset by infiniteBounce.animateValue(
        initialValue = if (isReverse) -limit else 0.dp,
        targetValue = if (isReverse) 0.dp else -limit,
        typeConverter = Dp.VectorConverter,
        animationSpec = spec
    )
    Icon(
        painter = painterResource(INFO_STYLUS),
        contentDescription = INFO_STYLUS_DESCR,
        modifier = modifier.requiredSize(52.dp).offset(xOffset, yOffset),
        tint = Color.Unspecified
    )
}

@Preview
@Composable
private fun AboutMemoScreenPreview() {
    VoltorbFlipTheme {
        TopScreen(Modifier.requiredSize(450.dp, 360.dp)) {
            AboutMemoScreen()
        }
    }
}