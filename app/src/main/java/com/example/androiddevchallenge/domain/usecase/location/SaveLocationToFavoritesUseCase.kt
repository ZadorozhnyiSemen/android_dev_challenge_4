package com.example.androiddevchallenge.domain.usecase.location

import com.example.androiddevchallenge.domain.bridge.UserPreferencesRepository
import com.example.androiddevchallenge.domain.entity.location.Location

class SaveLocationToFavoritesUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend operator fun invoke(location: Location) {
        userPreferencesRepository.addLocation(location)
    }
}