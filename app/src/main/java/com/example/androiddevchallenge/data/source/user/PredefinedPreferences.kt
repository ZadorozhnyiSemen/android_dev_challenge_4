package com.example.androiddevchallenge.data.source.user

import com.example.androiddevchallenge.data.entity.DataLocation

class PredefinedPreferences {
    fun get(): List<DataLocation> {
        return listOf(
            DataLocation("Tallinn", "Estonia", lat = 59.4370f, lng = 24.7536f),
            DataLocation("Colombo", "Sri Lanka", lat = 6.9271f, lng = 79.8612f),
            DataLocation("Nuugaatsiaq", "Greenland", lat = 71.5358f, lng = 53.2121f)
        )
    }
}