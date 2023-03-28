package dev.bogwalk.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.style.*

fun DrawScope.drawInsetBackground() {
    inset(5.5.dp.toPx()) {
        drawRect(lightGreen,
            Offset(x = size.width / 3, y = 0f),
            Size(size.width / 3, size.height / 3)
        )
        drawRect(lightGreen,
            Offset(x = size.width / 3, y = size.height / 3 * 2),
            Size(size.width / 3, size.height / 3)
        )
        drawRect(lightGreen,
            Offset(x = 0f, y = size.height / 3),
            Size(size.height / 3, size.width / 3)
        )
        drawRect(lightGreen,
            Offset(x = size.width / 3 * 2, y = size.height / 3),
            Size(size.height / 3, size.width / 3)
        )
    }
}

@Composable
fun InsetPattern(rows: Int) {
    val size = 42.dp / 3

    Box(
        Modifier
        .requiredHeight(4.dp + size * rows)
        .fillMaxWidth()
        .background(darkGreen)
        .drawBehind {
            var yStart = 4.dp.toPx()
            var alpha = 1f
            for (i in 1..rows) {
                var xStart = if (i % 2 == 0) 0f else size.toPx()
                while (xStart < this.size.width) {
                    drawRect(
                        lightGreen,
                        Offset(xStart, yStart),
                        Size(size.toPx(), size.toPx()),
                        alpha
                    )
                    xStart += size.toPx() * 2
                }
                yStart += size.toPx()
                alpha -= .1f
            }
        }
    )
}

fun DrawScope.drawLineBorder(
    outerColor: Color? = null,
    innerColor: Color? = null
) {
    when {
        outerColor != null -> drawBorder(4.dp.toPx(), 0f, outerColor, StrokeCap.Butt)
        innerColor != null -> drawBorder(2.dp.toPx(), 3.dp.toPx(), innerColor)
        else -> drawBorder(2.dp.toPx(), 4.5.dp.toPx(), mediumRed)
    }
}

fun DrawScope.drawInFocusBorders(isMemoOpen: Boolean) {
    if (isMemoOpen) {
        drawBorder(2.dp.toPx(), 0f, memoBrown, StrokeCap.Butt)
        drawBorder(4.dp.toPx(), 2.2.dp.toPx(), memoYellow)
    } else {
        drawBorder(2.dp.toPx(), 0f, darkRed, StrokeCap.Butt)
        drawBorder(4.dp.toPx(), 2.2.dp.toPx(), brightRed)
    }
}

fun DrawScope.drawBorder(
    stroke: Float,
    extra: Float,
    color: Color,
    cap: StrokeCap = StrokeCap.Round
) {
    drawLine(color,
        Offset(extra, extra),
        Offset(size.width - extra, extra),
        stroke, cap)
    drawLine(color,
        Offset(size.width - extra, extra),
        Offset(size.width - extra, size.height - extra),
        stroke, cap)
    drawLine(color,
        Offset(extra, extra),
        Offset(extra, size.height - extra),
        stroke, cap)
    drawLine(color,
        Offset(extra, size.height - extra),
        Offset(size.width - extra, size.height - extra),
        stroke, cap)
}