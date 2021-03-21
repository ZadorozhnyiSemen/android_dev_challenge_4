package com.example.androiddevchallenge.domain.usecase.weather

import com.example.androiddevchallenge.domain.bridge.WeatherRepository
import com.example.androiddevchallenge.domain.entity.location.Location
import com.example.androiddevchallenge.domain.entity.weather.Weather
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForecastInfoForLocationUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {

    suspend operator fun invoke(location: Location): Flow<Weather> =
        weatherRepository.loadWeatherForecast(location)
}