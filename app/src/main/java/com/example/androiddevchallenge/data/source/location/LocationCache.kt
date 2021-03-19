package com.example.androiddevchallenge.data.source.location

import com.example.androiddevchallenge.data.entity.DataLocation

class LocationCache {

    private val cachedLocations = mutableMapOf<String, List<DataLocation>>()

    fun save(query: String, locations: List<DataLocation>) {
        if (query !in cachedLocations) cachedLocations[query] = locations
    }

    fun load(query: String): List<DataLocation> {
        return cachedLocations[query] ?: emptyList()
    }
}