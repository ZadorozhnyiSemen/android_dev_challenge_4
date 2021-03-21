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
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SaveLocationToFavoritesUseCaseTest {
    private val mockPreferencesRepository = mockk<UserPreferencesRepository>()
    private val testLocation = Location("Test", "Country", 2f, 3f)

    val saveLocationToFavoritesUseCase = SaveLocationToFavoritesUseCase(mockPreferencesRepository)

    @Test
    fun `Use case should call interface to save location`() = runBlocking {
        coEvery { mockPreferencesRepository.addLocation(testLocation) } returns Unit

        saveLocationToFavoritesUseCase(testLocation)

        coVerify { mockPreferencesRepository.addLocation(testLocation) }
    }
}
