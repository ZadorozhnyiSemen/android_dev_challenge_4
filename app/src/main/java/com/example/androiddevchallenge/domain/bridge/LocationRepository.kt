package com.example.androiddevchallenge.domain.bridge

import com.example.androiddevchallenge.domain.entity.location.Location

import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    fun searchPlaces(query: String): Flow<List<Location>>
}