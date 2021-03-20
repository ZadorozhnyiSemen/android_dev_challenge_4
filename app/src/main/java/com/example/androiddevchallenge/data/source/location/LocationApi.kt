package com.example.androiddevchallenge.data.source.location

import com.example.androiddevchallenge.data.entity.DataLocation

class LocationApi() {

    suspend fun getLocationsByName(name: String): List<DataLocation> {
        return listOf()
    }
}