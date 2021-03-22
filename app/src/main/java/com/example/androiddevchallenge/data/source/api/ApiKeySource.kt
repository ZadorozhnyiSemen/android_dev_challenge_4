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
package com.example.androiddevchallenge.data.source.api

import javax.inject.Inject

class ApiKeySource @Inject constructor() {

    /**
     * https://www.troposphere.io/ api key (here only for demonstration purposes)
     */
    fun getApiKey(): String = "c3fddc7e5bcbb29f27360ca987341e0a59a50eb024f0885bfb"
}
