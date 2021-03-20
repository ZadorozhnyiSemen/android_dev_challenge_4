package com.example.androiddevchallenge.data.mappers

import com.example.androiddevchallenge.data.entity.DataCurrent
import com.example.androiddevchallenge.data.entity.DataDaily
import com.example.androiddevchallenge.data.entity.DataHourly
import com.example.androiddevchallenge.data.entity.DataWeather
import com.example.androiddevchallenge.domain.entity.weather.Current
import com.example.androiddevchallenge.domain.entity.weather.Daily
import com.example.androiddevchallenge.domain.entity.weather.Hourly
import com.example.androiddevchallenge.domain.entity.weather.Weather

class WeatherMapper(
    private val locationMapper: LocationMapper,
    private val forecastMapper: ForecastMapper
): Mapper<DataWeather, Weather> {
    override fun mapToDomain(dataValue: DataWeather): Weather {
        return Weather(
            locationMapper.mapToDomain(dataValue.dataLocation),
            Current(forecastMapper.mapToDomain(dataValue.current.forecast)),
            Hourly(forecastMapper.mapListToDomain(dataValue.hourly.data)),
            Daily(forecastMapper.mapListToDomain(dataValue.daily.data))
        )
    }

    override fun mapToData(domainValue: Weather): DataWeather {
        return DataWeather(
            locationMapper.mapToData(domainValue.location),
            DataCurrent(forecastMapper.mapToData(domainValue.current.forecast)),
            DataHourly(forecastMapper.mapListToData(domainValue.hourly.hourlyForecast)),
            DataDaily(forecastMapper.mapListToData(domainValue.daily.dailyForecast))
        )
    }

    override fun mapListToDomain(dataValue: List<DataWeather>): List<Weather> {
        return dataValue.map { mapToDomain(it) }
    }

    override fun mapListToData(domainValue: List<Weather>): List<DataWeather> {
        return domainValue.map { mapToData(it) }
    }
}