package com.example.androiddevchallenge.data.source.user

import com.example.androiddevchallenge.data.entity.DataLocation

class PreferencesCache {
    private val locationCache = mutableSetOf<DataLocation>()

    fun getLocations(): List<DataLocation> {
        return locationCache.toList()
    }

    fun addLocation(location: DataLocation) {
        locationCache.add(location)
    }

    fun addLocation(locations: List<DataLocation>) {
        locationCache.addAll(locations)
    }

    fun removeLocation(location: DataLocation) {
        locationCache.remove(location)
    }
}