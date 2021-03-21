package com.example.androiddevchallenge.data.repository

import com.example.androiddevchallenge.data.entity.DataLocation
import com.example.androiddevchallenge.data.mappers.LocationMapper
import com.example.androiddevchallenge.data.source.location.LocationCache
import com.example.androiddevchallenge.data.source.location.LocationNetworkSource
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
import org.junit.Assert.*
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