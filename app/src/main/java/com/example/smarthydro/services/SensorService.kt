package com.example.smarthydro.services

import com.example.smarthydro.models.SensorModel
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


private const val BASE_URL = "http://192.168.8.14/"

object SensorService{
    var gson = GsonBuilder()
        .setLenient()
        .create()
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
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

    @GET("history")
    suspend fun historicData(): List<SensorModel>
}



