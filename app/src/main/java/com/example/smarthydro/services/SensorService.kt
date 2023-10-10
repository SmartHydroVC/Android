package com.example.smarthydro.services

import com.example.smarthydro.models.SensorModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
private const val BASE_URL = "http://192.168.8.14/"

object SensorService{
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ISensors::class.java)
    }

    fun buildService(): ISensors {
        return retrofit
    }
}

interface ISensors {
    @GET("/r/n/r/n")
    suspend fun getSensorData(): SensorModel
}



