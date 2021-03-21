package com.example.androiddevchallenge.domain.usecase.location

import com.example.androiddevchallenge.domain.bridge.LocationRepository
import com.example.androiddevchallenge.domain.entity.location.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke(query: String): Flow<List<Location>> = if (query.length <= 4) {
        emptyList<List<Location>>().asFlow()
    } else {
        locationRepository.searchPlaces(query)
    }
}