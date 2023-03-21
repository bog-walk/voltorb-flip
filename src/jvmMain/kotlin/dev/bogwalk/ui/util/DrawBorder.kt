package dev.bogwalk.ui.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import dev.bogwalk.ui.style.darkGrey

fun DrawScope.drawButtonBorder(innerColor: Color? = null) {
    val (stroke, extra, color) = if (innerColor == null) {
        Triple(6.6f, 0.8f, darkGrey)
    } else {
        Triple(3f, 5.4f, innerColor)
    }

    drawBorder(stroke, extra, color)
}

fun DrawScope.drawBorder(
    stroke: Float,
    extra: Float,
    color: Color
) {
    val cap = StrokeCap.Round

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