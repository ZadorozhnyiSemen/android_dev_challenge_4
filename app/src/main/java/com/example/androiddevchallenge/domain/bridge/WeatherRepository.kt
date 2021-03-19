package com.example.androiddevchallenge.domain.bridge

import com.example.androiddevchallenge.common.Result
import com.example.androiddevchallenge.domain.entity.location.Location
import com.example.androiddevchallenge.domain.entity.weather.Forecast

interface WeatherRepository {

    suspend fun loadWeatherForecast(location: Location): Result<Forecast>
}