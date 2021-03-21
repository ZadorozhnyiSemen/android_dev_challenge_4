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
            return@let // API-KEY allows 1000 requests per month. Get from cache if possible
        }

        val weatherFromApi = weatherNetworkSource.requestWeather(dataLocation)
        weatherCache.saveWeather(weatherFromApi)
        emit(weatherMapper.mapToDomain(weatherFromApi))
    }.flowOn(ioDispatcher)
}
