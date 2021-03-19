package com.example.androiddevchallenge.domain.bridge

import com.example.androiddevchallenge.common.Result
import com.example.androiddevchallenge.domain.entity.location.Location

interface UserPreferencesRepository {

    suspend fun getUserLocations(): Result<List<Location>>

    suspend fun selectActiveLocation(location: Location)

    suspend fun addLocation(location: Location)
}