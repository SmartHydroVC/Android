package com.example.smarthydro.ui.theme.screen.home

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.smarthydro.models.SensorModel

data class Feature(
    val title: String,
    @DrawableRes val iconId: Int,
    val lightColor: Color,
    val mediumColor: Color,
    val darkColor: Color,
    val sensorReading: String
)