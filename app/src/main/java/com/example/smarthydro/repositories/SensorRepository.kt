package com.example.smarthydro.repositories

import com.example.smarthydro.models.SensorModel
import com.example.smarthydro.services.SensorService

class SensorRepository {
    private val sensorService = SensorService.buildService()

    suspend fun getSensorData(): SensorModel {
        return sensorService.getSensorData()
    }

    suspend fun getHistoricData(): List<SensorModel>{
        return sensorService.historicData()
    }
}