package com.example.smarthydro.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D

class OnOffAnimation {
    class UiState(
        val arcValue: Float = 0f
    )
    suspend fun onAnimation(animation: Animatable<Float, AnimationVector1D>) {
        animation.animateTo(1.00f)
    }

    suspend fun offAnimation(animation: Animatable<Float, AnimationVector1D>) {
        animation.animateTo(0.0f)
    }

    fun Animatable<Float, AnimationVector1D>.toUiState() = UiState(
        arcValue = value
    )

}