package com.example.androiddevchallenge.domain.usecase.weather

import com.example.androiddevchallenge.common.Result
import com.example.androiddevchallenge.domain.bridge.WeatherRepository
import com.example.androiddevchallenge.domain.entity.location.Location
import com.example.androiddevchallenge.domain.entity.weather.Forecast

class GetForecastInfoForLocationUseCase(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(location: Location): Result<Forecast> =
        weatherRepository.loadWeatherForecast(location)
}