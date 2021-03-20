package com.example.androiddevchallenge.data.repository

import com.example.androiddevchallenge.data.mappers.LocationMapper
import com.example.androiddevchallenge.data.mappers.WeatherMapper
import com.example.androiddevchallenge.data.source.weather.WeatherCache
import com.example.androiddevchallenge.data.source.weather.WeatherNetworkSource
import com.example.androiddevchallenge.domain.bridge.WeatherRepository
import com.example.androiddevchallenge.domain.entity.location.Location
import com.example.androiddevchallenge.domain.entity.weather.Weather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WeatherRepositoryImpl(
    private val weatherNetworkSource: WeatherNetworkSource,
    private val weatherCache: WeatherCache,
    private val locationMapper: LocationMapper,
    private val weatherMapper: WeatherMapper,
    private val ioDispatcher: CoroutineDispatcher
) : WeatherRepository {

    override suspend fun loadWeatherForecast(location: Location): Flow<Weather> = flow {
        val dataLocation = locationMapper.mapToData(location)
        weatherCache.getWeather(dataLocation)?.let {
            emit(weatherMapper.mapToDomain(it))
        }

        val weatherFromApi = weatherNetworkSource.requestWeather(dataLocation)
        weatherCache.saveWeather(weatherFromApi)
        emit(weatherMapper.mapToDomain(weatherFromApi))
    }.flowOn(ioDispatcher)
}