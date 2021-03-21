package com.example.androiddevchallenge.data.source.weather

import com.example.androiddevchallenge.data.entity.DataCurrent
import com.example.androiddevchallenge.data.entity.DataDaily
import com.example.androiddevchallenge.data.entity.DataForecast
import com.example.androiddevchallenge.data.entity.DataHourly
import com.example.androiddevchallenge.data.entity.DataLocation
import com.example.androiddevchallenge.data.entity.DataWeather
import com.example.androiddevchallenge.data.source.api.ApiKeySource
import javax.inject.Inject

class WeatherNetworkSource @Inject constructor(
    private val weatherApi: WeatherApi,
    private val apiKeySource: ApiKeySource
) {

    suspend fun requestWeather(dataLocation: DataLocation): DataWeather {
        val coords = "${dataLocation.lat},${dataLocation.lng}"
        return weatherApi.getWeather(coords, apiKeySource.getApiKey()).toData(dataLocation)
    }
}

data class WeatherResponse(
    val data: ResponseData
) {
    fun toData(location: DataLocation): DataWeather {
        return DataWeather(location, DataCurrent(
            DataForecast(this.data.current.time, this.data.current.type, this.data.current.temperature)),
            DataHourly(this.data.hourly.map { item -> DataForecast(item.time, item.type, item.temperature) }),
            DataDaily(this.data.daily.map { item -> DataForecast(item.time, item.type, item.temperature) })
        )
    }
}

data class ResponseData(
    val current: ResponseForecast,
    val hourly: List<ResponseForecast>,
    val daily: List<ResponseForecast>
)

data class ResponseForecast(
    val time: String,
    val type: String,
    val temperature: Float
)