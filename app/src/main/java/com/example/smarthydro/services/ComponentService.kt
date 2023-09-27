package com.example.smarthydro.services

import com.example.smarthydro.models.SensorModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://192.168.8.14/"
object ComponentService {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IComponent::class.java)
    }

    fun buildService(): IComponent {
        return retrofit
    }
}



interface IComponent {
    @GET("light")
    suspend fun toggleLight()
    @GET("fan")
    suspend fun toggleFan()
    @GET("extractor")
    suspend fun toggleExtractor()
    @GET("pump")
    suspend fun togglePump()
}