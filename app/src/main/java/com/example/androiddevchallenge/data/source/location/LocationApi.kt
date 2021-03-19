package com.example.androiddevchallenge.data.source.location

import com.example.androiddevchallenge.data.entity.DataLocation

class LocationApi {

    suspend fun getLocationsByName(name: String): List<DataLocation> {
        return listOf(
            DataLocation("Moscow", 12.4f, 12.5f),
            DataLocation("New York", 12.4f, 12.5f),
            DataLocation("Tallin", 12.4f, 12.5f),
            DataLocation("Test", 12.4f, 12.5f),
        )
    }
}