package com.example.androiddevchallenge.data.entity

data class DataWeather(
    val dataLocation: DataLocation,
    val current: DataCurrent,
    val hourly: DataHourly,
    val daily: DataDaily
)

data class DataCurrent(
    val forecast: DataForecast
)

data class DataHourly(
    val data: List<DataForecast>
)

data class DataDaily(
    val data: List<DataForecast>
)

data class DataForecast(
    val time: String,
    val type: String,
    val temperature: Float
)