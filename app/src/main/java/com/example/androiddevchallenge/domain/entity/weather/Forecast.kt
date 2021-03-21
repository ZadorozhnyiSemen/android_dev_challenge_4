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
    object Sun: WeatherType()
    object Cloud: WeatherType()
    object Rain: WeatherType()
    object Snow: WeatherType()
    object Other: WeatherType()
}

data class Forecast(
    val temperature: Int,
    val type: WeatherType,
    val weekDay: WeekDay,
    val hourOfDay: String
)

sealed class WeekDay {
    object Sunday: WeekDay()
    object Monday: WeekDay()
    object Tuesday: WeekDay()
    object Wednesday: WeekDay()
    object Thursday: WeekDay()
    object Friday: WeekDay()
    object Saturday: WeekDay()
}