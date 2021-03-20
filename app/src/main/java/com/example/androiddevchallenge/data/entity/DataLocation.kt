package com.example.androiddevchallenge.data.entity

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class DataLocation(
    val name: String,
    val country: String,
    val lat: Float,
    val lng: Float
) {
    @Id
    var id = name.hashCode().toLong()
}