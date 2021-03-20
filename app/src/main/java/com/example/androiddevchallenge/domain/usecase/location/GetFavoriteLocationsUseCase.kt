package com.example.androiddevchallenge.domain.usecase.location

import com.example.androiddevchallenge.domain.bridge.UserPreferencesRepository
import com.example.androiddevchallenge.domain.entity.location.Location
import kotlinx.coroutines.flow.Flow

class GetFavoriteLocationsUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend operator fun invoke(): Flow<List<Location>> =
        userPreferencesRepository.getUserLocations()
}