package com.example.androiddevchallenge.data.repository

import com.example.androiddevchallenge.data.entity.DataCurrent
import com.example.androiddevchallenge.data.entity.DataDaily
import com.example.androiddevchallenge.data.entity.DataForecast
import com.example.androiddevchallenge.data.entity.DataHourly
import com.example.androiddevchallenge.data.entity.DataLocation
import com.example.androiddevchallenge.data.entity.DataWeather
import com.example.androiddevchallenge.data.mappers.LocationMapper
import com.example.androiddevchallenge.data.mappers.WeatherMapper
import com.example.androiddevchallenge.data.source.weather.WeatherCache
import com.example.androiddevchallenge.data.source.weather.WeatherNetworkSource
import com.example.androiddevchallenge.domain.entity.location.Location
import com.example.androiddevchallenge.domain.entity.weather.Current
import com.example.androiddevchallenge.domain.entity.weather.Daily
import com.example.androiddevchallenge.domain.entity.weather.Forecast
import com.example.androiddevchallenge.domain.entity.weather.Hourly
import com.example.androiddevchallenge.domain.entity.weather.Weather
import com.example.androiddevchallenge.domain.entity.weather.WeatherType
import com.example.androiddevchallenge.domain.entity.weather.WeekDay
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryImplTest {
    val mockNetwork = mockk<WeatherNetworkSource>()
    val mockCache = mockk<WeatherCache>()
    val mockLocationMapper = mockk<LocationMapper>()
    val mockWeatherMapper = mockk<WeatherMapper>()

    val repo = WeatherRepositoryImpl(
        mockNetwork,
        mockCache,
        mockLocationMapper,
        mockWeatherMapper,
        TestCoroutineDispatcher()
    )

    private val testLocation = Location("Test11", "Country", 2f, 3f)
    private val testDataLocation = DataLocation("Test11", "Country", 2f, 3f)
    private val testForecast = Forecast(1, WeatherType.Sun, WeekDay.Friday, "f")
    private val testDataForecast = DataForecast("", "", 1f)
    private val testLocationWeather = DataWeather(dataLocation = testDataLocation, DataCurrent(testDataForecast), DataHourly(listOf(testDataForecast)), DataDaily(listOf(testDataForecast)))
    private val testDomainLocationWeather = Weather(location = testLocation, Current(testForecast), Hourly(listOf(testForecast)), Daily(listOf(testForecast)))

    @Test
    fun `If cache exists - newer load from network`() = runBlockingTest {
        coEvery { mockLocationMapper.mapToData(testLocation) } returns testDataLocation
        coEvery { mockCache.getWeather(testDataLocation) } returns testLocationWeather
        coEvery { mockWeatherMapper.mapToDomain(testLocationWeather) } returns testDomainLocationWeather

        repo.loadWeatherForecast(testLocation).first()

        coVerify { mockLocationMapper.mapToData(testLocation) }
        coVerify { mockCache.getWeather(testDataLocation) }
        coVerify(exactly = 0) { mockNetwork.requestWeather(testDataLocation) }
    }

    @Test
    fun `If no cache exists - load from network and save in cache`() = runBlockingTest {
        coEvery { mockLocationMapper.mapToData(testLocation) } returns testDataLocation
        coEvery { mockCache.getWeather(testDataLocation) } returns null
        coEvery { mockCache.saveWeather(testLocationWeather) } returns Unit
        coEvery { mockNetwork.requestWeather(testDataLocation) } returns testLocationWeather
        coEvery { mockWeatherMapper.mapToDomain(testLocationWeather) } returns testDomainLocationWeather

        repo.loadWeatherForecast(testLocation).first()

        coVerify { mockLocationMapper.mapToData(testLocation) }
        coVerify { mockCache.getWeather(testDataLocation) }
        coVerify { mockNetwork.requestWeather(testDataLocation) }
        coVerify { mockWeatherMapper.mapToDomain(testLocationWeather) }
        coVerify { mockCache.saveWeather(testLocationWeather) }
    }
}