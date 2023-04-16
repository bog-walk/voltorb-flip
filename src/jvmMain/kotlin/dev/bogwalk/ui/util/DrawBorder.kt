package dev.bogwalk.ui.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import dev.bogwalk.ui.style.*

/**
 * Draws outer and inner borders for custom composables with a pixelated finish.
 */
fun DrawScope.drawLineBorder(
    outerColor: Color? = null,
    innerColor: Color? = null
) {
    when {
        outerColor != null -> drawBorder(thickBorder.toPx(), 0f, outerColor, StrokeCap.Butt)
        innerColor != null -> drawBorder(thinBorder.toPx(), 3.dp.toPx(), innerColor)
        else -> drawBorder(thinBorder.toPx(), 4.5.dp.toPx(), mediumRed)
    }
}

/**
 * Draws outer and inner borders for custom composables with a rounded finish.
 */
fun DrawScope.drawInFocusBorders(isMemoOpen: Boolean) {
    if (isMemoOpen) {
        drawBorder(thinBorder.toPx(), 0f, memoBrown, StrokeCap.Butt)
        drawBorder(thickBorder.toPx(), 2.2.dp.toPx(), memoYellow)
    } else {
        drawBorder(thinBorder.toPx(), 0f, darkRed, StrokeCap.Butt)
        drawBorder(thickBorder.toPx(), 2.2.dp.toPx(), brightRed)
    }
}

/**
 * Draws a rectangular border using 4 lines.
 */
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