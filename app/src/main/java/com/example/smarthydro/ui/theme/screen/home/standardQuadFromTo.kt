package com.example.smarthydro.ui.theme.screen.home

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import kotlin.math.abs

fun Path.standardQuadFromTo(from: Offset, to: Offset) {
    quadraticBezierTo(
        from.x,
        from.y,
        abs(from.x + to.x) / 2f,
        abs(from.y + to.y) / 2f
    )
}


fun createMediumColoredPath(width: Float, height: Float): Path {
    // Medium colored path
    val mediumColoredPoint1 = Offset(0f, height * 0.3f)
    val mediumColoredPoint2 = Offset(width * 0.1f, height * 0.35f)
    val mediumColoredPoint3 = Offset(width * 0.4f, height * 0.05f)
    val mediumColoredPoint4 = Offset(width * 0.75f, height * 0.7f)
    val mediumColoredPoint5 = Offset(width * 1.4f, -height)

    return coloredPath(
        width = width,
        height = height,
        colorPoint1 = mediumColoredPoint1,
        colorPoint2 = mediumColoredPoint2,
        colorPoint3 = mediumColoredPoint3,
        colorPoint4 = mediumColoredPoint4,
        colorPoint5 = mediumColoredPoint5
    )
}
fun createLightColoredPath(width: Float, height: Float): Path {
    // Light colored path
    val lightPoint1 = Offset(0f, height * 0.35f)
    val lightPoint2 = Offset(width * 0.1f, height * 0.4f)
    val lightPoint3 = Offset(width * 0.3f, height * 0.35f)
    val lightPoint4 = Offset(width * 0.65f, height)
    val lightPoint5 = Offset(width * 1.4f, -height / 3f)

    return coloredPath(
        width = width,
        height = height,
        colorPoint1 = lightPoint1,
        colorPoint2 = lightPoint2,
        colorPoint3 = lightPoint3,
        colorPoint4 = lightPoint4,
        colorPoint5 = lightPoint5
    )
}

private fun coloredPath(
    width: Float,
    height: Float,
    colorPoint1: Offset,
    colorPoint2: Offset,
    colorPoint3: Offset,
    colorPoint4: Offset,
    colorPoint5: Offset,
) : Path {

    return Path().apply {
        moveTo(colorPoint1.x, colorPoint1.y)
        standardQuadFromTo(colorPoint1, colorPoint2)
        standardQuadFromTo(colorPoint2, colorPoint3)
        standardQuadFromTo(colorPoint3, colorPoint4)
        standardQuadFromTo(colorPoint4, colorPoint5)
        lineTo(width + 100f, height + 100f)
        lineTo(-100f, height + 100f)
        close()
    }
}