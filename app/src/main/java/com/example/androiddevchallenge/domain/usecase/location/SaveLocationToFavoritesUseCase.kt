package com.example.androiddevchallenge.domain.usecase.location

import com.example.androiddevchallenge.domain.bridge.UserPreferencesRepository
import com.example.androiddevchallenge.domain.entity.location.Location
import javax.inject.Inject

class SaveLocationToFavoritesUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend operator fun invoke(location: Location) {
        userPreferencesRepository.addLocation(location)
    }
}