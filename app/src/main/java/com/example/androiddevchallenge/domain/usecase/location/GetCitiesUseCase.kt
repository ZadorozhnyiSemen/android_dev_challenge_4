package com.example.androiddevchallenge.domain.usecase.location

import com.example.androiddevchallenge.common.Result
import com.example.androiddevchallenge.domain.bridge.LocationRepository
import com.example.androiddevchallenge.domain.entity.location.City

class GetCitiesUseCase(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke(query: String): Result<List<City>> =
        locationRepository.searchPlaces(query)
}