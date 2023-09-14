package com.example.smarthydro.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val TextWhite = Color(0xffeeeeee)
val DeepBlue = Color(0xff06164c)
val ButtonBlue = Color(0xff505cf3)
val DarkerButtonBlue = Color(0xff566894)
val LightRed = Color(0xfffc879a)
val AquaBlue = Color(0xff9aa5c4)
val OrangeYellow1 = Color(0xfff0bd28)
val OrangeYellow2 = Color(0xfff1c746)
val OrangeYellow3 = Color(0xfff4cf65)
val Beige1 = Color(0xfffdbda1)
val Beige2 = Color(0xfffcaf90)
val Beige3 = Color(0xfff9a27b)
val LightGreen1 = Color(0xff54e1b6)
val LightGreen2 = Color(0xff36ddab)
val LightGreen3 = Color(0xff11d79b)
val BlueViolet1 = Color(0xffaeb4fd)
val BlueViolet2 = Color(0xff9fa5fe)
val BlueViolet3 = Color(0xff8f98fd)
val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Red1 = Color(0xfffc879a)
val Red2 = Color(0xFFBD5D6D)
val Red3 = Color(0xFF993646)
val Teal200 = Color(0xFF03DAC5)
val PrimaryColor = Color(0xFFFDA858)
val BackgroundColor = Color(0xFFFFE8F0)
val LightTextColor = Color(0xFF758684)
val SecondaryColor = Color(0xFF593153)
val PlaceholderColor = Color(0xFFE5E5E5)
val LightPrimaryColor = Color(0xFFFFF6EE)

val GreenGradient = Brush.linearGradient(
    colors = listOf(LightGreen1, LightGreen2),
    start = Offset(0f, 0f),
    end = Offset(Float.POSITIVE_INFINITY, 0f)
)

val DarkGradient = Brush.verticalGradient(
    colors = listOf(DeepBlue, DarkerButtonBlue)
)