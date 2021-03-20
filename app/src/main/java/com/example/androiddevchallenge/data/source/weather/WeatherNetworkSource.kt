package com.example.androiddevchallenge.data.source.weather

import com.example.androiddevchallenge.data.entity.DataCurrent
import com.example.androiddevchallenge.data.entity.DataDaily
import com.example.androiddevchallenge.data.entity.DataForecast
import com.example.androiddevchallenge.data.entity.DataHourly
import com.example.androiddevchallenge.data.entity.DataLocation
import com.example.androiddevchallenge.data.entity.DataWeather

class WeatherNetworkSource {

    private val forecast = DataForecast("","clear", 10f)

    fun requestWeather(dataLocation: DataLocation): DataWeather {
        return DataWeather(
            dataLocation,
            DataCurrent(forecast = forecast),
            DataHourly(listOf(forecast, forecast)),
            DataDaily(listOf(forecast, forecast))
        )
    }
}