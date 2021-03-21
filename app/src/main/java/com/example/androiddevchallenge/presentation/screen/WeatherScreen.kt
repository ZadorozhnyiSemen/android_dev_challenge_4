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
package com.example.androiddevchallenge.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.domain.entity.location.Location
import com.example.androiddevchallenge.domain.entity.weather.Weather
import com.example.androiddevchallenge.presentation.components.DailyWeather
import com.example.androiddevchallenge.presentation.components.HourlyWeather
import com.example.androiddevchallenge.presentation.components.LocationList
import com.example.androiddevchallenge.presentation.components.RotatingBottomSheet
import com.example.androiddevchallenge.presentation.components.SearchLocation
import com.example.androiddevchallenge.presentation.components.Temperature
import com.example.androiddevchallenge.presentation.components.WeatherBackground
import com.example.androiddevchallenge.ui.theme.AppTheme
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@Composable
fun WeatherScreen(
    selectedItem: LiveData<Weather>,
    selectedLocation: LiveData<Location>,
    favoriteLocations: LiveData<List<Location>>,
    searchResult: LiveData<List<Location>>,
    searchQuery: LiveData<String>,
    onCitySelected: (Location) -> Unit,
    onQueryChanged: (String) -> Unit,
    onCityAdded: (Location) -> Unit
) {
    val selectedWeather by selectedItem.observeAsState()
    val selectedItem by selectedLocation.observeAsState()
    val locations by favoriteLocations.observeAsState()
    val searchResult by searchResult.observeAsState()
    val currentSearchQuery by searchQuery.observeAsState()

    Surface {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .background(Brush.verticalGradient(AppTheme.colors.bg_gradient))
        ) {
            WeatherBackground(
                forecast = selectedWeather?.current?.forecast,
                modifier = Modifier
                    .fillMaxSize(),
                bgModifier = Modifier.padding(bottom = 120.dp),
                accompanistModifier = Modifier.align(Alignment.TopEnd)
            )

            LocationList(
                locations,
                selectedItem,
                onCitySelected,
                modifier = Modifier.semantics { heading() })

            selectedWeather?.let {
                Temperature(
                    it.current.forecast,
                    modifier = Modifier
                        .paddingFromBaseline(top = 162.dp)
                        .padding(start = 32.dp)
                )
            }

            SearchLocation(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 24.dp, top = 84.dp, start = 24.dp),
                onQueryChanged = onQueryChanged,
                onCityAdded = onCityAdded,
                locations = searchResult,
                query = currentSearchQuery
            )

            RotatingBottomSheet(
                leftIcon = {
                    Icon(
                        modifier = Modifier
                            .clip(
                                CircleShape
                            )
                            .background(AppTheme.colors.secondary)
                            .size(56.dp)
                            .padding(16.dp),
                        painter = painterResource(id = R.drawable.ic_houtry),
                        contentDescription = stringResource(
                            id = R.string.cd_icon_hourly
                        )
                    )
                },
                leftContent = {
                    HourlyWeather(
                        hourly = selectedWeather?.hourly,
                        modifier = Modifier
                            .padding(32.dp)
                            .fillMaxWidth()
                    )
                },
                rightIcon = {
                    Icon(
                        modifier = Modifier
                            .clip(
                                CircleShape
                            )
                            .background(AppTheme.colors.secondary)
                            .size(56.dp)
                            .padding(16.dp),
                        painter = painterResource(id = R.drawable.ic_daily),
                        contentDescription = stringResource(
                            id = R.string.cd_icon_daily
                        )
                    )
                },
                rightContent = {
                    DailyWeather(
                        daily = selectedWeather?.daily,
                        modifier = Modifier
                            .padding(32.dp)
                            .fillMaxWidth()
                    )
                },
                closeIcon = {
                    Icon(
                        modifier = Modifier
                            .clip(
                                CircleShape
                            )
                            .background(AppTheme.colors.secondary)
                            .size(56.dp)
                            .padding(16.dp),
                        painter = painterResource(id = R.drawable.ic_cross),
                        contentDescription = stringResource(
                            id = R.string.cd_icon_close
                        )
                    )
                },
                title = {
                    Text(
                        modifier = Modifier.clearAndSetSemantics {},
                        text = "Weather details",
                        style = AppTheme.typography.button.copy(color = AppTheme.colors.onSurface)
                    )
                },
                boxSize = maxWidth,
                modifier = Modifier
                    .size(maxWidth)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}
