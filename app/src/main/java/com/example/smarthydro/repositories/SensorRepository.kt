package com.example.smarthydro.repositories

import com.example.smarthydro.models.SensorModel
import com.example.smarthydro.models.NewSensorModel
import com.example.smarthydro.models.NewSensorModelItem
import com.example.smarthydro.services.SensorService

class SensorRepository {
    private val sensorService = SensorService.buildService()

    suspend fun getSensorData(): List<NewSensorModelItem> {
        return sensorService.getSensorData()
    }
}