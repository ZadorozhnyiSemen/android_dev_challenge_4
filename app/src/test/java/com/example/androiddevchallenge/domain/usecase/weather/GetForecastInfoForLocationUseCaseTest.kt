package com.example.androiddevchallenge.domain.usecase.weather

import com.example.androiddevchallenge.domain.bridge.WeatherRepository
import com.example.androiddevchallenge.domain.entity.location.Location
import com.example.androiddevchallenge.domain.entity.weather.Current
import com.example.androiddevchallenge.domain.entity.weather.Daily
import com.example.androiddevchallenge.domain.entity.weather.Forecast
import com.example.androiddevchallenge.domain.entity.weather.Hourly
import com.example.androiddevchallenge.domain.entity.weather.Weather
import com.example.androiddevchallenge.domain.entity.weather.WeatherType
import com.example.androiddevchallenge.domain.entity.weather.WeekDay
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetForecastInfoForLocationUseCaseTest {

    private val mockWeatherRepository = mockk<WeatherRepository>()
    private val testLocation = Location("Test", "Country", 2f, 3f)
    private val testForecast = Forecast(1, WeatherType.Cloud, WeekDay.Saturday, "4pm")
    private val testLocationWeather = Weather(testLocation, Current(testForecast), Hourly(listOf(testForecast)), Daily(listOf(testForecast)))

    val getForecastInfoForLocationUseCase = GetForecastInfoForLocationUseCase(mockWeatherRepository)

    @Test
    fun `Use case should return weather for given location`() = runBlocking {
        coEvery { mockWeatherRepository.loadWeatherForecast(testLocation) } returns flowOf(testLocationWeather)

        val weatherResult = getForecastInfoForLocationUseCase(testLocation).first()

        assertEquals(testLocation, weatherResult.location)
    }
}