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
import com.example.androiddevchallenge.data.source.location.LocationCache
import com.example.androiddevchallenge.data.source.location.LocationNetworkSource
import com.example.androiddevchallenge.domain.entity.location.Location
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

class LocationRepositoryImplTest {
    val locationApi = mockk<LocationNetworkSource>()
    val locationCache = mockk<LocationCache>()
    val locationMapper = mockk<LocationMapper>()

    val locationRepositoryImpl = LocationRepositoryImpl(
        locationApi,
        locationCache,
        locationMapper,
        TestCoroutineDispatcher()
    )

    private val testLocation = listOf(Location("Test11", "Country", 2f, 3f))
    private val testDataLocation = listOf(DataLocation("Test11", "Country", 2f, 3f))

    @Test
    fun `If query cached return cached value`() = runBlockingTest {
        coEvery { locationCache.load("test") } returns testDataLocation
        coEvery { locationMapper.mapListToDomain(testDataLocation) } returns testLocation

        val locations = locationRepositoryImpl.searchPlaces("test").first()

        assertEquals(locations, testLocation)
        coVerify { locationCache.load("test") }
        coVerify(exactly = 0) { locationApi.getLocationsByName("test") }
    }

    @Test
    fun `If cache empty - load from api and save in cache`() = runBlockingTest {
        coEvery { locationCache.load("test") } returns emptyList()
        coEvery { locationApi.getLocationsByName("test") } returns testDataLocation
        coEvery { locationCache.save("test", testDataLocation) } returns Unit
        coEvery { locationMapper.mapListToDomain(testDataLocation) } returns testLocation

        val locations = locationRepositoryImpl.searchPlaces("test").first()

        assertEquals(locations, testLocation)
        coVerify { locationCache.load("test") }
        coVerify { locationCache.save("test", testDataLocation) }
        coVerify { locationApi.getLocationsByName("test") }
    }
}
