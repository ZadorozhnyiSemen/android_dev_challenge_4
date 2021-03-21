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
package com.example.androiddevchallenge.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.domain.entity.weather.Forecast
import com.example.androiddevchallenge.domain.entity.weather.WeatherType
import com.example.androiddevchallenge.presentation.theme.AppTheme

@Composable
fun Temperature(
    forecast: Forecast,
    modifier: Modifier = Modifier
) {
    val forecastType = when (forecast.type) {
        WeatherType.Sun -> stringResource(id = R.string.weather_sun)
        WeatherType.Cloud -> stringResource(id = R.string.weather_clouds)
        WeatherType.Rain -> stringResource(id = R.string.weather_rain)
        WeatherType.Snow -> stringResource(id = R.string.cd_weather_snow)
        WeatherType.Other -> ""
    }
    Row(modifier = modifier.semantics(mergeDescendants = true) {}) {
        Text(
            text = "${forecast.temperature}°",
            style = AppTheme.typography.h3.copy(color = AppTheme.colors.onBackground),
            modifier = Modifier.alignByBaseline()
        )
        Text(
            text = forecastType,
            style = AppTheme.typography.h2.copy(color = AppTheme.colors.onBackground),
            modifier = Modifier
                .alignByBaseline()
                .offset(x = (-24).dp)
        )
    }
}
