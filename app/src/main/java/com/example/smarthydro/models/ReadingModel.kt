package com.example.smarthydro.models

data class Reading(
    var heading: String,
    var sensorModel: SensorModel,
    var unit: String,
    var readingValue: String
)

fun getReadingUnit(readingString: String, data: SensorModel): Reading {
    val reading = when (readingString) {
        "Temperature" -> Pair("C", data.temperature)
        "Water Level" -> Pair("L/hr", data.flowRate)
        "Clean Water" -> Pair("pH", data.pH)
        "Humidity" -> Pair("RH", data.humidity)
        "Compost" -> Pair("ms/cm", data.eC)
        "Sun Light" -> Pair("lux", data.light)
        else -> Pair("", "")
    }

    return Reading(readingString, SensorModel(), reading.first, reading.second)
        .apply {
            heading = readingString
            readingValue = reading.second
            unit = reading.first
        }
}
