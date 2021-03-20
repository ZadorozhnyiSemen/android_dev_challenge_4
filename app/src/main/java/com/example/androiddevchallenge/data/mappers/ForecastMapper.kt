package com.example.androiddevchallenge.data.mappers

import com.example.androiddevchallenge.data.entity.DataForecast
import com.example.androiddevchallenge.domain.entity.weather.Forecast
import com.example.androiddevchallenge.domain.entity.weather.WeatherType

class ForecastMapper : Mapper<DataForecast, Forecast> {
    override fun mapToDomain(dataValue: DataForecast): Forecast {
        val type = when(dataValue.type) {
            "clear" -> WeatherType.Sun
            "partly-cloudy", "cloudy", "dust", "mist", "fog" -> WeatherType.Cloud
            "rain", "drizzle", "rain-showers", "rain-snow-shower", "thunderstorm" -> WeatherType.Rain
            "snow", "snowdrifting", "snow-shower", "hail", "snow-hail" -> WeatherType.Snow
            else -> WeatherType.Other
        }
        return Forecast(dataValue.temperature, type)
    }

    override fun mapToData(domainValue: Forecast): DataForecast {
        val type = when (domainValue.type) {
            WeatherType.Sun -> "clear"
            WeatherType.Cloud -> "cloudy"
            WeatherType.Rain -> "rain"
            WeatherType.Snow -> "show"
            WeatherType.Other -> "other"
        }
        return DataForecast("0", type, domainValue.temperature)
    }

    override fun mapListToDomain(dataValue: List<DataForecast>): List<Forecast> {
        return dataValue.map { mapToDomain(it) }
    }

    override fun mapListToData(domainValue: List<Forecast>): List<DataForecast> {
        return domainValue.map { mapToData(it) }
    }
}