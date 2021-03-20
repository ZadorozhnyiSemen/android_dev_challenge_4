package com.example.androiddevchallenge.data.source.weather

import com.example.androiddevchallenge.data.entity.DataLocation
import com.example.androiddevchallenge.data.entity.DataWeather

class WeatherCache {
    private val weatherCache = mutableMapOf<String, DataWeather>()

    fun getWeather(location: DataLocation): DataWeather? {
        return weatherCache[location.name]
    }

    fun saveWeather(dataWeather: DataWeather) {
        weatherCache[dataWeather.dataLocation.name] = dataWeather
    }
}