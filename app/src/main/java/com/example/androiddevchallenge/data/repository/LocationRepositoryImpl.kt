/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
) : LocationRepository {

    override fun searchPlaces(query: String): Flow<List<Location>> = flow {
        val cached = cache.load(query)
        if (cached.isNotEmpty()) {
            emit(locationMapper.mapListToDomain(cached))
            return@flow
        }

        val locations = api.getLocationsByName(query)
        cache.save(query, locations)
        emit(locationMapper.mapListToDomain(locations))
    }.flowOn(ioDispatcher)
}
