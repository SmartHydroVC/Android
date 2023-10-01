package com.example.smarthydro.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthydro.models.NewSensorModelItem
import com.example.smarthydro.models.SensorModel
import com.example.smarthydro.repositories.SensorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SensorViewModel : ViewModel() {
    private val repository = SensorRepository()

    private val _sensorData = MutableLiveData<List<NewSensorModelItem>>()
    val sensorData: LiveData<List<NewSensorModelItem>> = _sensorData


    fun fetchSensorData() {
        viewModelScope.launch {
            try {
                val data = repository.getSensorData()
                //Log.i("DATA", "$data")
                _sensorData.value = data
            } catch (e: Exception) {
                Log.e("SENSOR ERROR", e.message.toString())
            }
        }
    }

    fun fetchSensorPeriodically(milliseconds: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                fetchSensorData()
                Log.i("FETCHED DATA", "THE DATA HAS BEEN FETCHED")
                Log.i("DATA", "${sensorData.value}")
                delay(milliseconds)
            }
        }
    }
}