package com.example.androiddevchallenge.data.repository

import com.example.androiddevchallenge.data.mappers.LocationMapper
import com.example.androiddevchallenge.data.source.location.LocationCache
import com.example.androiddevchallenge.data.source.location.LocationNetworkSource
import com.example.androiddevchallenge.domain.bridge.LocationRepository
import com.example.androiddevchallenge.domain.entity.location.Location
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LocationRepositoryImpl(
    private val api: LocationNetworkSource,
    private val cache: LocationCache,
    private val locationMapper: LocationMapper,
    private val ioDispatcher: CoroutineDispatcher
): LocationRepository {

    override fun searchPlaces(query: String): Flow<List<Location>> = flow {
        emit(locationMapper.mapListToDomain(cache.load(query)))
        val locations = api.getLocationsByName(query)
        cache.save(query, locations)
        emit(locationMapper.mapListToDomain(locations))
    }.flowOn(ioDispatcher)
}