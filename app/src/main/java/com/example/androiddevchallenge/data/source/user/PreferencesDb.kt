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
import io.objectbox.Box
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor
import javax.inject.Inject

class PreferencesDb @Inject constructor(
    private val objectBox: BoxStore?
) {

    private val preferencesBox: Box<DataLocation>? = objectBox?.boxFor()

    fun getLocations(): List<DataLocation> = preferencesBox?.all ?: emptyList()

    fun storeLocation(location: DataLocation) {
        preferencesBox?.put(location)
    }

    fun removeLocation(location: DataLocation) {
        preferencesBox?.remove(location)
    }
}
