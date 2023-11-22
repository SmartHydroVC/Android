package com.example.smarthydro.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.smarthydro.models.Reading

class ReadingViewModel: ViewModel() {
    private var readingTypeViewModel by mutableStateOf<Reading?>(null)

    fun setReadingType(type: Reading){
        readingTypeViewModel = type
    }

    fun getReadingType(): Reading?{
        return readingTypeViewModel
    }

}