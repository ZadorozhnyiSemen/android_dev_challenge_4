package com.example.androiddevchallenge.domain.bridge

import com.example.androiddevchallenge.domain.entity.location.Location
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    suspend fun getUserLocations(): Flow<List<Location>>

    suspend fun addLocation(location: Location)
}