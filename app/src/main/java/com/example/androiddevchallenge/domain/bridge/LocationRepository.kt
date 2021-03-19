package com.example.androiddevchallenge.domain.bridge

import com.example.androiddevchallenge.domain.entity.location.City

import com.example.androiddevchallenge.common.Result

interface LocationRepository {

    fun searchPlaces(query: String): Result<List<City>>
}