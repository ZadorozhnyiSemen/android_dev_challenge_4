package com.example.androiddevchallenge.domain.usecase.location

import com.example.androiddevchallenge.domain.bridge.UserPreferencesRepository
import com.example.androiddevchallenge.domain.entity.location.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteLocationsUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {

    suspend operator fun invoke(): Flow<List<Location>> =
        userPreferencesRepository.getUserLocations().map { list ->
            list.forEach {
              it.selected = false
            }
            list
        }
}