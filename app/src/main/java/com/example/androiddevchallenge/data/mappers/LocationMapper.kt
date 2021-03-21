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
package com.example.androiddevchallenge.data.mappers

import com.example.androiddevchallenge.data.entity.DataLocation
import com.example.androiddevchallenge.domain.entity.location.Location
import javax.inject.Inject

class LocationMapper @Inject constructor() : Mapper<DataLocation, Location> {

    override fun mapToDomain(dataValue: DataLocation): Location {
        return Location(dataValue.name, dataValue.country, dataValue.lat, dataValue.lng)
    }

    override fun mapToData(domainValue: Location): DataLocation {
        return DataLocation(domainValue.name, domainValue.country, domainValue.latitude, domainValue.longitude)
    }

    override fun mapListToDomain(dataValue: List<DataLocation>): List<Location> {
        return dataValue.map { mapToDomain(it) }
    }

    override fun mapListToData(domainValue: List<Location>): List<DataLocation> {
        return domainValue.map { mapToData(it) }
    }
}
