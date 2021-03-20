package com.example.androiddevchallenge.domain.bridge

import com.example.androiddevchallenge.domain.entity.location.Location
import com.example.androiddevchallenge.domain.entity.weather.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun loadWeatherForecast(location: Location): Flow<Weather>
}