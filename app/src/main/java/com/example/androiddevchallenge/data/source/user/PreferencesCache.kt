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
package com.example.androiddevchallenge.data.source.user

import com.example.androiddevchallenge.data.entity.DataLocation
import javax.inject.Inject

class PreferencesCache @Inject constructor() {
    private val locationCache = mutableSetOf<DataLocation>()

    fun getLocations(): List<DataLocation> {
        return locationCache.toList()
    }

    fun addLocation(location: DataLocation) {
        locationCache.add(location)
    }

    fun addLocation(locations: List<DataLocation>) {
        locationCache.addAll(locations)
    }

    fun removeLocation(location: DataLocation) {
        locationCache.remove(location)
    }
}
