package com.example.androiddevchallenge.domain.usecase.location

import com.example.androiddevchallenge.common.Result
import com.example.androiddevchallenge.domain.bridge.UserPreferencesRepository
import com.example.androiddevchallenge.domain.entity.location.Location

class GetFavoriteLocationsUseCase(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend operator fun invoke(): Result<List<Location>> =
        userPreferencesRepository.getUserLocations()
}