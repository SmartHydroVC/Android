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

    suspend fun ecUp() {
        return componentService.ecUp()
    }

    suspend fun ecDown() {
        return componentService.ecDown()
    }

    suspend fun phUp() {
        return componentService.pHUp()
    }

    suspend fun phDown() {
        return componentService.pHDown()
    }

    suspend fun toggleFan() {
        return componentService.toggleFan()
    }
}