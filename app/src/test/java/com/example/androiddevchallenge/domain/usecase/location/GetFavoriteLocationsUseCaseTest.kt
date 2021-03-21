/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.domain.usecase.location

import com.example.androiddevchallenge.domain.bridge.UserPreferencesRepository
import com.example.androiddevchallenge.domain.entity.location.Location
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetFavoriteLocationsUseCaseTest {
    private val userPreferencesRepository = mockk<UserPreferencesRepository>()
    private val testLocation1 = Location("Test", "Country", 2f, 3f, true)
    private val testLocation2 = Location("Test1", "Country1", 3f, 2f, true)

    val getFavoriteLocationsUseCase = GetFavoriteLocationsUseCase(userPreferencesRepository)

    @Test
    fun `Use case should call repository for locations`() = runBlocking {
        coEvery { userPreferencesRepository.getUserLocations() } returns flowOf(listOf(testLocation1, testLocation2))

        val locations = getFavoriteLocationsUseCase().first()

        assertEquals(locations[0], testLocation1)
        assertEquals(locations[1], testLocation2)
        coVerify { userPreferencesRepository.getUserLocations() }
    }

    @Test
    fun `Use case should switch 'selected' flag`() = runBlocking {
        coEvery { userPreferencesRepository.getUserLocations() } returns flowOf(listOf(testLocation1, testLocation2))

        val locations = getFavoriteLocationsUseCase().first()

        locations.forEach {
            assertEquals(false, it.selected)
        }

        coVerify { userPreferencesRepository.getUserLocations() }
    }
}
