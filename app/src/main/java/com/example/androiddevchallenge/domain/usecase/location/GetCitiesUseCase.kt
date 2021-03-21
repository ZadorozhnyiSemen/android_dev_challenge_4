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
package com.example.androiddevchallenge.domain.usecase.location

import com.example.androiddevchallenge.domain.bridge.LocationRepository
import com.example.androiddevchallenge.domain.entity.location.Location
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

/**
 * Domain use case.
 *
 * Logic:
 * Load locations by given query.
 *
 * If query size less or equal 4 then return empty result.
 * Optimization for https://www.troposphere.io/ limited api quota. (~420 request left)
 */
class GetCitiesUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {

    suspend operator fun invoke(query: String): Flow<List<Location>> = if (query.length <= 4) {
        emptyList<List<Location>>().asFlow()
    } else {
        locationRepository.searchPlaces(query)
    }
}
