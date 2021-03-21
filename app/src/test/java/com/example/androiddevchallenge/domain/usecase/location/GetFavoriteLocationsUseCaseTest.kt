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

