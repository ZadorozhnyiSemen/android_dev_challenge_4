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
package com.example.androiddevchallenge.data.source.weather

import com.example.androiddevchallenge.data.entity.DataCurrent
import com.example.androiddevchallenge.data.entity.DataDaily
import com.example.androiddevchallenge.data.entity.DataForecast
import com.example.androiddevchallenge.data.entity.DataHourly
import com.example.androiddevchallenge.data.entity.DataLocation
import com.example.androiddevchallenge.data.entity.DataWeather
import com.example.androiddevchallenge.data.source.api.ApiKeySource
import javax.inject.Inject

class WeatherNetworkSource @Inject constructor(
    private val weatherApi: WeatherApi,
    private val apiKeySource: ApiKeySource
) {

    suspend fun requestWeather(dataLocation: DataLocation): DataWeather =
        weatherApi.getWeather(dataLocation.coordsAsString(), apiKeySource.getApiKey()).toData(dataLocation)
}

data class WeatherResponse(
    val data: ResponseData
) {
    fun toData(location: DataLocation): DataWeather {
        return DataWeather(
            location,
            DataCurrent(
                DataForecast(this.data.current.time, this.data.current.type, this.data.current.temperature)
            ),
            DataHourly(this.data.hourly.map { item -> DataForecast(item.time, item.type, item.temperature) }),
            DataDaily(this.data.daily.map { item -> DataForecast(item.time, item.type, item.temperature) })
        )
    }
}

data class ResponseData(
    val current: ResponseForecast,
    val hourly: List<ResponseForecast>,
    val daily: List<ResponseForecast>
)

data class ResponseForecast(
    val time: String,
    val type: String,
    val temperature: Float
)
