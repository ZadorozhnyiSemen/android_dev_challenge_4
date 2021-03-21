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
package com.example.androiddevchallenge.domain.entity.weather

import com.example.androiddevchallenge.domain.entity.location.Location

data class Weather(
    val location: Location,
    val current: Current,
    val hourly: Hourly,
    val daily: Daily
)

data class Current(
    val forecast: Forecast
)

data class Hourly(
    val hourlyForecast: List<Forecast>
)

data class Daily(
    val dailyForecast: List<Forecast>
)

sealed class WeatherType {
    object Sun : WeatherType()
    object Cloud : WeatherType()
    object Rain : WeatherType()
    object Snow : WeatherType()
    object Other : WeatherType()
}

data class Forecast(
    val temperature: Int,
    val type: WeatherType,
    val weekDay: WeekDay,
    val hourOfDay: String
)

sealed class WeekDay {
    object Sunday : WeekDay()
    object Monday : WeekDay()
    object Tuesday : WeekDay()
    object Wednesday : WeekDay()
    object Thursday : WeekDay()
    object Friday : WeekDay()
    object Saturday : WeekDay()
}
