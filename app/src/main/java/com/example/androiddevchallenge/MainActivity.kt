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
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import com.example.androiddevchallenge.presentation.screen.WeatherScreen
import com.example.androiddevchallenge.presentation.screen.WeatherScreenViewModel
import com.example.androiddevchallenge.presentation.theme.system.LocalSysUiController
import com.example.androiddevchallenge.presentation.theme.system.SystemUiController
import com.example.androiddevchallenge.ui.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val weatherScreenViewModel by viewModels<WeatherScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            CompositionLocalProvider(LocalSysUiController provides systemUiController) {
                ProvideWindowInsets {
                    MyApp(weatherScreenViewModel)
                }
            }
        }
    }
}

@Composable
fun MyApp(
    weatherScreenViewModel: WeatherScreenViewModel,
) {
    val weatherItem by weatherScreenViewModel.selectedItem.observeAsState()

    WeatherTheme(weatherItem?.current?.forecast?.type) {
        WeatherScreen(
            weatherScreenViewModel.selectedItem,
            weatherScreenViewModel.selectedLocation,
            weatherScreenViewModel.favoriteLocations,
            weatherScreenViewModel.searchResult,
            weatherScreenViewModel.searchQuery,
            weatherScreenViewModel::onCitySelected,
            weatherScreenViewModel::onQueryChanged,
            weatherScreenViewModel::onCityAdded
        )
    }
}

