package com.example.smarthydro.repositories


import com.example.smarthydro.services.ComponentService


class ComponentRepository {
    private val componentService = ComponentService.buildService()

    suspend fun toggleLight() {
        return componentService.toggleLight()
    }

    suspend fun togglePump() {
        return componentService.togglePump()
    }

    suspend fun toggleExtractor() {
        return componentService.toggleExtractor()
    }

    suspend fun toggleFan() {
        return componentService.toggleFan()
    }
}