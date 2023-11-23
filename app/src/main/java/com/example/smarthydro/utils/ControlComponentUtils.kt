package com.example.smarthydro.utils

import android.content.res.Resources
import com.example.smarthydro.R
import com.example.smarthydro.viewmodels.ComponentViewModel

class ControlComponentUtils(private val componentViewModel: ComponentViewModel) {
    fun controlComponent(readingType: String) {
        when (readingType) {
            "Temperature" -> componentViewModel.setFan()
            "Sun Light" -> componentViewModel.setLight()
            "Humidity" -> componentViewModel.setExtractor()
            "Water Level"-> componentViewModel.setPump()
            "Clean Water" -> componentViewModel.setPh()
            "Compost" -> componentViewModel.setEc()
        }
    }
}