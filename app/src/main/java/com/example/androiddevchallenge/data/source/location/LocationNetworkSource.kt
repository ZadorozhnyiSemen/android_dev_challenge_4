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
package com.example.androiddevchallenge.data.source.location

import com.example.androiddevchallenge.data.entity.DataLocation
import com.example.androiddevchallenge.data.source.api.ApiKeySource
import javax.inject.Inject

class LocationNetworkSource @Inject constructor(
    private val locationApi: LocationApi,
    private val apiKeySource: ApiKeySource
) {

    suspend fun getLocationsByName(name: String): List<DataLocation> {
        val key = apiKeySource.getApiKey()
        // Server can't handle requests with spaces at the end use trim()
        return locationApi.getPlaces(name.trim(), key).toData()
    }
}
