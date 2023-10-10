package com.example.smarthydro.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthydro.repositories.ComponentRepository
import kotlinx.coroutines.launch

class ComponentViewModel : ViewModel() {
    private val repository = ComponentRepository()

    fun setLight() {
        viewModelScope.launch {
            try {
                repository.toggleLight()
            } catch (e: Exception) {
                Log.e("LIGHT ERROR", e.message.toString())
            }
        }
    }

    fun setPump() {
        viewModelScope.launch {
            try {
                repository.togglePump()
            } catch (e: Exception) {
                Log.e("PUMP ERROR", e.message.toString())
            }
        }
    }

    fun setExtractor() {
        viewModelScope.launch {
            try {
                repository.toggleExtractor()
            } catch (e: Exception) {
                Log.e("EXTRACTOR ERROR", e.message.toString())
            }
        }
    }


    fun setFan() {
        viewModelScope.launch {
            try {
                repository.toggleFan()
            } catch (e: Exception) {
                Log.e("FAN ERROR", e.message.toString())
            }
        }
    }

}