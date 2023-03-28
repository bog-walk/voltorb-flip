package dev.bogwalk.ui.components

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.style.VoltorbFlipTheme
import dev.bogwalk.ui.style.darkGrey
import dev.bogwalk.ui.util.drawLineBorder

@Composable
fun SpeechBox(
    text: String
) {
    Box(
        Modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 10.dp)
            .background(Brush.verticalGradient(
                .7f to Color(0xff63636b),
                .8f to Color(0xff5a5a63), .85f to Color(0xff52525a)
            ))
            .drawBehind {
                // are these colours correct?
                drawLineBorder(outerColor = darkGrey)
                drawLineBorder(innerColor = Color(0xffffde6b))
                drawRoundRect(
                    Color.White,
                    Offset(15.dp.toPx(), 10.dp.toPx()),
                    Size(size.width * .85f, size.height - 20.dp.toPx()),
                    CornerRadius(2.dp.toPx())
                )
            }
    ) {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth(.9f)
                .padding(horizontal = 25.dp, vertical = 15.dp),
            maxLines = 2,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
private fun SpeechBoxPreview() {
    VoltorbFlipTheme {
        Column {
            SpeechBox("Play VOLTORB Flip Lv. 1?\n")
            SpeechBox("Here is an example of some long text that should break")
        }
    }
}