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