package com.example.smarthydro.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthydro.models.SensorModel
import com.example.smarthydro.repositories.ComponentRepository
import com.example.smarthydro.repositories.SensorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ComponentViewModel : ViewModel() {
    private val repository = ComponentRepository()

    fun setLight() {
        viewModelScope.launch {
            try {
                val data = repository.toggleLight()
            } catch (e: Exception) {
                Log.e("COMPONENT ERROR", e.message.toString())
            }
        }
    }

    fun setPump() {
        viewModelScope.launch {
            try {
                val data = repository.togglePump()
            } catch (e: Exception) {
                Log.e("COMPONENT ERROR", e.message.toString())
            }
        }
    }

    fun setExtractor() {
        viewModelScope.launch {
            try {
                val data = repository.toggleExtractor()
            } catch (e: Exception) {
                Log.e("COMPONENT ERROR", e.message.toString())
            }
        }
    }


    fun setFan() {
        viewModelScope.launch {
            try {
                val data = repository.toggleFan()
            } catch (e: Exception) {
                Log.e("COMPONENT ERROR", e.message.toString())
            }
        }
    }

}