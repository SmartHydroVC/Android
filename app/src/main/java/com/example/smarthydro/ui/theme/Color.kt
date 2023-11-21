package com.example.smarthydro.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val TextWhite = Color(0xffeeeeee)
val DeepBlue = Color(0xff06164c)
val DarkerButtonBlue = Color(0xff566894)
val AquaBlue = Color(0xff9aa5c4)
val OrangeYellow1 = Color(0xfff0bd28)
val OrangeYellow2 = Color(0xfff1c746)
val OrangeYellow3 = Color(0xfff4cf65)
val Beige1 = Color(0xfffdbda1)
val Beige2 = Color(0xfffcaf90)
val Beige3 = Color(0xfff9a27b)
val LightGreen1 = Color(0xFF54E181)
val LightGreen2 = Color(0xFF36DD81)
val LightGreen3 = Color(0xFF11D784)
val BlueViolet1 = Color(0xffaeb4fd)
val BlueViolet2 = Color(0xff9fa5fe)
val BlueViolet3 = Color(0xff8f98fd)
val Blue1 = Color(0xFF5A61BA)
val Blue2 = Color(0xFF4149C2)
val Blue3 = Color(0xFF2936CA)
val Red1 = Color(0xfffc879a)
val Red2 = Color(0xFFBD5D6D)
val Red3 = Color(0xFF993646)
val PrimaryColor = Color(0xFFFDA858)
val GreenGood = Color(0xff50c878 )
val RedBad = Color(0xFFD2042D)

val GreenGradient = Brush.linearGradient(
    colors = listOf(LightGreen1, LightGreen2),
    start = Offset(0f, 0f),
    end = Offset(Float.POSITIVE_INFINITY, 0f)
)

val DarkGradient = Brush.verticalGradient(
    colors = listOf(DeepBlue, DarkerButtonBlue)
)