package com.example.androiddevchallenge.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.domain.entity.weather.Forecast
import com.example.androiddevchallenge.domain.entity.weather.WeatherType
import com.example.androiddevchallenge.ui.theme.AppTheme

@Composable
fun Temperature(
    forecast: Forecast,
    modifier: Modifier = Modifier
) {
    val forecastType = when (forecast.type) {
        WeatherType.Sun -> "Sunny"
        WeatherType.Cloud -> "Cloudy"
        WeatherType.Other -> ""
        WeatherType.Rain -> "Rainy"
        WeatherType.Snow -> "Snowy"
    }
    Row(modifier = modifier.semantics(mergeDescendants = true) {}) {
        Text(
            text = "${forecast.temperature}°",
            style = AppTheme.typography.h3.copy(color = AppTheme.colors.onBackground),
            modifier = Modifier.alignByBaseline()
        )
        Text(text = forecastType,
            style = AppTheme.typography.h2.copy(color = AppTheme.colors.onBackground),
            modifier = Modifier
            .alignByBaseline()
            .offset(x = -24.dp))
    }
}