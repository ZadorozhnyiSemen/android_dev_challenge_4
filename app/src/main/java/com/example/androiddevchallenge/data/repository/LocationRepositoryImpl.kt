package com.example.androiddevchallenge.data.repository

import com.example.androiddevchallenge.data.mappers.LocationMapper
import com.example.androiddevchallenge.data.source.location.LocationApi
import com.example.androiddevchallenge.data.source.location.LocationCache
import com.example.androiddevchallenge.domain.bridge.LocationRepository
import com.example.androiddevchallenge.domain.entity.location.Location
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LocationRepositoryImpl(
    private val api: LocationApi,
    private val cache: LocationCache,
    private val locationMapper: LocationMapper,
    private val ioDispatcher: CoroutineDispatcher
): LocationRepository {

    override fun searchPlaces(query: String): Flow<List<Location>> = flow {
        emit(locationMapper.mapToDomain(cache.load(query)))
        val locations = api.getLocationsByName(query)
        cache.save(query, locations)
        delay(500)
        emit(locationMapper.mapToDomain(locations))
    }.flowOn(ioDispatcher)
}