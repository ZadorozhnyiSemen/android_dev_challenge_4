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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.domain.entity.weather.Forecast
import com.example.androiddevchallenge.domain.entity.weather.Hourly
import com.example.androiddevchallenge.domain.entity.weather.WeatherType
import com.example.androiddevchallenge.presentation.theme.AppTheme

@Composable
fun HourlyWeather(
    hourly: Hourly?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = stringResource(R.string.from_today), style = AppTheme.typography.h2.copy(color = AppTheme.colors.onBackground))
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
            items(hourly?.hourlyForecast ?: emptyList()) { item ->
                HourlyItem(hourlyForecast = item)
            }
        }
    }
}

@Composable
fun HourlyItem(
    hourlyForecast: Forecast
) {

    val iconRes = when (hourlyForecast.type) {
        WeatherType.Sun -> R.drawable.ic_sun
        WeatherType.Rain -> R.drawable.ic_rain
        WeatherType.Snow -> R.drawable.ic_snow
        else -> R.drawable.ic_cloud
    }

    val weatherContentDescription = when (hourlyForecast.type) {
        WeatherType.Rain -> stringResource(id = R.string.cd_weather_rain)
        WeatherType.Snow -> stringResource(id = R.string.cd_weather_snow)
        WeatherType.Sun -> stringResource(id = R.string.cd_weather_sun)
        else -> stringResource(id = R.string.cd_weather_clouds)
    }

    val cdFull = "${hourlyForecast.hourOfDay} $weatherContentDescription ${hourlyForecast.temperature}??"

    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.semantics(true) { contentDescription = cdFull }.fillMaxWidth()) {
        Text(modifier = Modifier.width(40.dp), maxLines = 1, overflow = TextOverflow.Ellipsis, text = hourlyForecast.hourOfDay, style = AppTheme.typography.button.copy(color = AppTheme.colors.onBackground))
        Icon(painter = painterResource(id = iconRes), tint = AppTheme.colors.primary, contentDescription = null)
        Text(modifier = Modifier.width(40.dp), text = "${hourlyForecast.temperature}??", style = AppTheme.typography.button.copy(color = AppTheme.colors.onBackground))
    }
}
