package com.example.androiddevchallenge.data.source.weather

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    @GET("forecast/{coordinates}")
    suspend fun getWeather(@Path("coordinates") coords: String, @Query("token") apiKey: String): WeatherResponse
}