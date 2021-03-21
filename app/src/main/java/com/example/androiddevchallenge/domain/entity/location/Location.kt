package com.example.androiddevchallenge.domain.entity.location

data class Location(
    val name: String,
    val country: String,
    val latitude: Float,
    val longitude: Float,
    var selected: Boolean? = false
)
