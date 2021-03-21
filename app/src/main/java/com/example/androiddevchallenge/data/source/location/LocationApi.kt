package com.example.androiddevchallenge.data.source.location

import com.example.androiddevchallenge.data.entity.DataLocation
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationApi {

    @GET("place/name/{place}")
    suspend fun getPlaces(@Path("place") place: String, @Query("token") apiKey: String): PlacesResponse
}

data class PlacesResponse(
    val data: List<CityResponse>?
) {
    fun toData(): List<DataLocation> {
        return this.data?.map { DataLocation(it.name, it.country, it.latitude, it.longitude) } ?: emptyList()
    }
}

data class CityResponse(
    val name: String,
    val country: String,
    val latitude: Float,
    val longitude: Float
)