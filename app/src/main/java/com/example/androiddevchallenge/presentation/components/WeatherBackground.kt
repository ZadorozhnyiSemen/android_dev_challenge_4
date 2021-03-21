package com.example.androiddevchallenge.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.domain.entity.weather.Forecast
import com.example.androiddevchallenge.domain.entity.weather.WeatherType

@Composable
fun WeatherBackground(
    forecast: Forecast?,
    modifier: Modifier = Modifier,
    bgModifier: Modifier = Modifier,
    accompanistModifier: Modifier = Modifier
) {
    val background = when (forecast?.type) {
        WeatherType.Sun -> R.drawable.ic_background_sun
        WeatherType.Rain -> R.drawable.ic_background_rain
        WeatherType.Cloud -> R.drawable.ic_background_cloudy
        WeatherType.Snow -> R.drawable.ic_background_snow
        else -> R.drawable.ic_background_cloudy
    }

    Box(modifier = modifier) {
        WeatherAccompanists(forecast = forecast, modifier = accompanistModifier)
        Image(modifier = bgModifier
            .fillMaxWidth()
            .align(alignment = Alignment.BottomCenter), painter = painterResource(id = background), contentDescription = null, contentScale = ContentScale.FillBounds)
        PersonImage(forecast = forecast, modifier = Modifier.align(Alignment.BottomEnd))
    }
}

@Composable
fun PersonImage(
    forecast: Forecast?,
    modifier: Modifier = Modifier
) {
    val image = when(forecast?.type) {
        WeatherType.Cloud -> R.drawable.ic_temp_20_weather_unknown
        WeatherType.Rain -> {
            when {
                forecast.temperature.toInt() in (Int.MIN_VALUE..10) -> R.drawable.ic_temp_0_10_weather_rain
                forecast.temperature.toInt() in (10..20) -> R.drawable.ic_temp_10_20_weather_rain
                forecast.temperature.toInt() in (20..Int.MAX_VALUE) -> R.drawable.ic_temp_20_weather_rain
                else -> R.drawable.ic_temp_20_weather_unknown
            }
        }
        WeatherType.Snow -> {
            when {
                forecast.temperature.toInt() in (Int.MIN_VALUE..0) -> R.drawable.ic_temp_0_weather_snow
                forecast.temperature.toInt() in (0..Int.MAX_VALUE) -> R.drawable.ic_temp_0_10_weather_snow
                else -> R.drawable.ic_temp_20_weather_unknown
            }
        }
        WeatherType.Sun -> {
            when {
                forecast.temperature.toInt() in (Int.MIN_VALUE..0) -> R.drawable.ic_temp_0_weather_sun
                forecast.temperature.toInt() in (0..10) -> R.drawable.ic_temp_0_10_weather_sun
                forecast.temperature.toInt() in (10..20) -> R.drawable.ic_temp_10_20_weather_sun
                forecast.temperature.toInt() in (20..Int.MAX_VALUE) -> R.drawable.ic_temp_20_weather_sun
                else -> R.drawable.ic_temp_20_weather_unknown
            }
        }
        WeatherType.Other -> R.drawable.ic_temp_20_weather_unknown
        null -> R.drawable.ic_temp_20_weather_unknown
    }

    Image(modifier = modifier, painter = painterResource(id = image), contentDescription = null)
}

@Composable
fun WeatherAccompanists(
    forecast: Forecast?,
    modifier: Modifier = Modifier
) {
    when (forecast?.type) {
        WeatherType.Sun -> SunAccompanist(modifier = modifier.padding(top = 94.dp, end = 32.dp))
        WeatherType.Rain -> RainAccompanist(modifier = modifier.padding(top = 112.dp).fillMaxWidth())
        WeatherType.Snow -> SnowAccompanist(modifier = modifier.padding(top = 62.dp))
        WeatherType.Cloud -> CloudAccompanist(modifier = modifier.padding(top = 107.dp).fillMaxWidth())
        else -> {}
    }
}

@Composable
fun SunAccompanist(
    modifier: Modifier = Modifier
) {
    Image(modifier = modifier, painter = painterResource(id = R.drawable.ic_sun_accomp), contentDescription = null)
}

@Composable
fun RainAccompanist(
    modifier: Modifier = Modifier
) {
    Image(modifier = modifier, painter = painterResource(id = R.drawable.ic_rain_clouds_accomp), contentDescription = null, contentScale = ContentScale.FillWidth)
}

@Composable
fun SnowAccompanist(
    modifier: Modifier = Modifier
) {
    Image(modifier = modifier, painter = painterResource(id = R.drawable.ic_snow_accomp), contentDescription = null)
}

@Composable
fun CloudAccompanist(
    modifier: Modifier = Modifier
) {
    Image(modifier = modifier, painter = painterResource(id = R.drawable.ic_rain_accomp), contentDescription = null, contentScale = ContentScale.FillWidth)
}