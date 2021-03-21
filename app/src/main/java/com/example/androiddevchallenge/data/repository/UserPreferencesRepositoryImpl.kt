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
import com.example.androiddevchallenge.data.source.user.PredefinedPreferences
import com.example.androiddevchallenge.data.source.user.PreferencesCache
import com.example.androiddevchallenge.data.source.user.PreferencesDb
import com.example.androiddevchallenge.domain.bridge.UserPreferencesRepository
import com.example.androiddevchallenge.domain.entity.location.Location
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class UserPreferencesRepositoryImpl(
    private val preferencesCache: PreferencesCache,
    private val preferencesDb: PreferencesDb,
    private val defaultPreferences: PredefinedPreferences,
    private val locationMapper: LocationMapper,
    private val ioDispatcher: CoroutineDispatcher
) : UserPreferencesRepository {

    override suspend fun getUserLocations(): Flow<List<Location>> = flow {
        if (preferencesCache.getLocations().isNotEmpty()) {
            emit(locationMapper.mapListToDomain(preferencesCache.getLocations()))
        } else {
            val dbLocations = preferencesDb.getLocations()
            if (dbLocations.isEmpty()) {
                emit(locationMapper.mapListToDomain(defaultPreferences.get()))
            } else {
                preferencesCache.addLocation(dbLocations)
                emit(locationMapper.mapListToDomain(dbLocations))
            }
        }
    }.flowOn(ioDispatcher)

    override suspend fun addLocation(location: Location) = withContext(ioDispatcher) {
        val dataLocation = locationMapper.mapToData(location)
        preferencesDb.storeLocation(dataLocation)
        preferencesCache.addLocation(dataLocation)
    }
}
