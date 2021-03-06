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
package com.example.androiddevchallenge.data.mappers

import com.example.androiddevchallenge.data.entity.DataForecast
import com.example.androiddevchallenge.domain.entity.weather.Forecast
import com.example.androiddevchallenge.domain.entity.weather.WeatherType
import com.example.androiddevchallenge.domain.entity.weather.WeekDay
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

class ForecastMapper @Inject constructor() : Mapper<DataForecast, Forecast> {
    override fun mapToDomain(dataValue: DataForecast): Forecast {
        val type = when (dataValue.type) {
            "clear" -> WeatherType.Sun
            "partly-cloudy", "cloudy", "dust", "mist", "fog" -> WeatherType.Cloud
            "rain", "drizzle", "rain-showers", "rain-snow-shower", "thunderstorm" -> WeatherType.Rain
            "snow", "snowdrifting", "snow-shower", "hail", "snow-hail" -> WeatherType.Snow
            else -> WeatherType.Other
        }
        println(dataValue.time)
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatted = format.parse(dataValue.time)
        val c = Calendar.getInstance()
        c.time = formatted
        val weekDay = when (c[Calendar.DAY_OF_WEEK]) {
            1 -> WeekDay.Sunday
            2 -> WeekDay.Monday
            3 -> WeekDay.Tuesday
            4 -> WeekDay.Wednesday
            5 -> WeekDay.Thursday
            6 -> WeekDay.Friday
            7 -> WeekDay.Saturday
            else -> WeekDay.Sunday
        }
        val justhour = c[Calendar.HOUR]
        val ampm = c[Calendar.AM_PM]
        return Forecast(dataValue.temperature.toInt(), type, weekDay, "${justhour + 1}${if (ampm == 1) "pm" else "am"}")
    }

    override fun mapToData(domainValue: Forecast): DataForecast {
        val type = when (domainValue.type) {
            WeatherType.Sun -> "clear"
            WeatherType.Cloud -> "cloudy"
            WeatherType.Rain -> "rain"
            WeatherType.Snow -> "show"
            WeatherType.Other -> "other"
        }
        return DataForecast("0", type, domainValue.temperature.toFloat())
    }

    override fun mapListToDomain(dataValue: List<DataForecast>): List<Forecast> {
        return dataValue.map { mapToDomain(it) }
    }

    override fun mapListToData(domainValue: List<Forecast>): List<DataForecast> {
        return domainValue.map { mapToData(it) }
    }
}
