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
package com.example.androiddevchallenge.data.repository

import com.example.androiddevchallenge.data.entity.DataLocation
import com.example.androiddevchallenge.data.mappers.LocationMapper
import com.example.androiddevchallenge.data.source.user.PredefinedPreferences
import com.example.androiddevchallenge.data.source.user.PreferencesCache
import com.example.androiddevchallenge.data.source.user.PreferencesDb
import com.example.androiddevchallenge.domain.entity.location.Location
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

class UserPreferencesRepositoryImplTest {

    val preferencesCache = mockk<PreferencesCache>()
    val preferencesDb = mockk<PreferencesDb>()
    val predefinedPreferences = mockk<PredefinedPreferences>()
    val locationMapper = mockk<LocationMapper>()

    val userPreferencesRepositoryImpl = UserPreferencesRepositoryImpl(
        preferencesCache,
        preferencesDb,
        predefinedPreferences,
        locationMapper,
        TestCoroutineDispatcher()
    )

    private val testLocation = listOf(Location("Test11", "Country", 2f, 3f))
    private val testDataLocation = listOf(DataLocation("Test11", "Country", 2f, 3f))

    @Test
    fun `If database and cache are empty - load predefined`() = runBlockingTest {
        coEvery { preferencesCache.getLocations() } returns emptyList()
        coEvery { preferencesDb.getLocations() } returns emptyList()
        coEvery { predefinedPreferences.get() } returns testDataLocation
        coEvery { locationMapper.mapListToDomain(testDataLocation) } returns testLocation

        val locations = userPreferencesRepositoryImpl.getUserLocations().first()

        assertEquals(locations, testLocation)
        coVerify { predefinedPreferences.get() }
    }

    @Test
    fun `If cache value - return cache immediately`() = runBlockingTest {
        coEvery { preferencesCache.getLocations() } returns testDataLocation
        coEvery { locationMapper.mapListToDomain(testDataLocation) } returns testLocation

        val locations = userPreferencesRepositoryImpl.getUserLocations().first()

        assertEquals(locations, testLocation)
        coVerify { preferencesCache.getLocations() }
        coVerify(exactly = 0) { preferencesDb.getLocations() }
        coVerify(exactly = 0) { predefinedPreferences.get() }
    }

    @Test
    fun `If database value - return database data and save in cache`() = runBlockingTest {
        coEvery { preferencesCache.getLocations() } returns emptyList()
        coEvery { preferencesDb.getLocations() } returns testDataLocation
        coEvery { locationMapper.mapListToDomain(testDataLocation) } returns testLocation
        coEvery { preferencesCache.addLocation(testDataLocation) } returns Unit

        val locations = userPreferencesRepositoryImpl.getUserLocations().first()

        assertEquals(locations, testLocation)
        coVerify { preferencesCache.getLocations() }
        coVerify { preferencesDb.getLocations() }
        coVerify { preferencesCache.addLocation(testDataLocation) }
        coVerify(exactly = 0) { predefinedPreferences.get() }
    }
}
