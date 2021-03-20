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
            }
            else {
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