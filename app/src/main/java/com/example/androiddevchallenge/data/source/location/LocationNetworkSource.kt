package com.example.androiddevchallenge.data.source.location

import com.example.androiddevchallenge.data.entity.DataLocation
import com.example.androiddevchallenge.data.source.api.ApiKeySource
import javax.inject.Inject

class LocationNetworkSource @Inject constructor(
    private val locationApi: LocationApi,
    private val apiKeySource: ApiKeySource
) {

    suspend fun getLocationsByName(name: String): List<DataLocation> {
        val key = apiKeySource.getApiKey()
        // Server can't handle requests with spaces at the end use trim()
        return locationApi.getPlaces(name.trim(), key).toData()
    }
}