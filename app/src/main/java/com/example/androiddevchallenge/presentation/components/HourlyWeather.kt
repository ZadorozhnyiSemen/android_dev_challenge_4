package com.example.androiddevchallenge.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.domain.entity.weather.Forecast
import com.example.androiddevchallenge.domain.entity.weather.Hourly
import com.example.androiddevchallenge.domain.entity.weather.WeatherType
import com.example.androiddevchallenge.ui.theme.AppTheme

@Composable
fun HourlyWeather(
    hourly: Hourly?,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(14.dp)) {
        items(hourly?.hourlyForecast ?: emptyList()) {item ->
            HourlyItem(hourlyForecast = item)
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

    val cdFull = "${hourlyForecast.hourOfDay} $weatherContentDescription ${hourlyForecast.temperature}°"

    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.semantics(true){contentDescription = cdFull}) {
        Text(modifier = Modifier.weight(2f), text = hourlyForecast.hourOfDay)
        Spacer(modifier = Modifier.width(42.dp))
        Icon(painter = painterResource(id = iconRes), tint = AppTheme.colors.primary, contentDescription = null)
        Spacer(modifier = Modifier.width(42.dp))
        Text("${hourlyForecast.temperature}°")
    }
}