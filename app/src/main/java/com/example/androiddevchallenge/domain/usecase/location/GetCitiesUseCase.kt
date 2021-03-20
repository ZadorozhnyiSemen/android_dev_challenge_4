package com.example.androiddevchallenge.domain.usecase.location

import com.example.androiddevchallenge.domain.bridge.LocationRepository
import com.example.androiddevchallenge.domain.entity.location.Location
import kotlinx.coroutines.flow.Flow

class GetCitiesUseCase(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke(query: String): Flow<List<Location>> =
        locationRepository.searchPlaces(query)
}