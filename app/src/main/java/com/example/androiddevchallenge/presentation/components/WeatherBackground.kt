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

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.domain.entity.weather.Forecast
import com.example.androiddevchallenge.domain.entity.weather.WeatherType
import com.example.androiddevchallenge.presentation.theme.AppTheme
import kotlin.random.Random

@Composable
fun WeatherBackground(
    forecast: Forecast?,
    modifier: Modifier = Modifier,
    parentWidth: Dp,
    parentHeight: Dp,
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
        WeatherAccompanists(
            forecast = forecast,
            parentWidth = parentWidth,
            parentHeight = parentHeight,
            modifier = accompanistModifier
        )
        Image(
            modifier = bgModifier
                .fillMaxWidth()
                .align(alignment = Alignment.BottomCenter),
            painter = painterResource(id = background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        PersonImage(forecast = forecast, modifier = Modifier.align(Alignment.BottomEnd))
    }
}

@Composable
fun PersonImage(
    forecast: Forecast?,
    modifier: Modifier = Modifier
) {
    val image = when (forecast?.type) {
        WeatherType.Cloud -> {
            when (forecast.temperature) {
                in (Int.MIN_VALUE until 0) -> R.drawable.ic_temp_0_weather_clouds
                in (0 until 10) -> R.drawable.ic_temp_0_10_weather_clouds
                in (10 until 20) -> R.drawable.ic_temp_10_20_weather_clouds
                in (20..Int.MAX_VALUE) -> R.drawable.ic_temp_20_weather_clouds
                else -> R.drawable.ic_temp_20_weather_unknown
            }
        }
        WeatherType.Rain -> {
            when (forecast.temperature) {
                in (Int.MIN_VALUE until 10) -> R.drawable.ic_temp_0_10_weather_rain
                in (10 until 20) -> R.drawable.ic_temp_10_20_weather_rain
                in (20..Int.MAX_VALUE) -> R.drawable.ic_temp_20_weather_rain
                else -> R.drawable.ic_temp_20_weather_unknown
            }
        }
        WeatherType.Snow -> {
            when (forecast.temperature) {
                in (Int.MIN_VALUE until 0) -> R.drawable.ic_temp_0_weather_snow
                in (0..Int.MAX_VALUE) -> R.drawable.ic_temp_0_10_weather_snow
                else -> R.drawable.ic_temp_20_weather_unknown
            }
        }
        WeatherType.Sun -> {
            when (forecast.temperature) {
                in (Int.MIN_VALUE until 0) -> R.drawable.ic_temp_0_weather_sun
                in (0 until 10) -> R.drawable.ic_temp_0_10_weather_sun
                in (10 until 20) -> R.drawable.ic_temp_10_20_weather_sun
                in (20..Int.MAX_VALUE) -> R.drawable.ic_temp_20_weather_sun
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
    parentWidth: Dp,
    parentHeight: Dp,
    modifier: Modifier = Modifier
) {
    var localForecast by remember(forecast?.type) { mutableStateOf<WeatherType>(WeatherType.Other) }

    if (forecast?.type != localForecast) {
        localForecast = forecast?.type ?: WeatherType.Other
    }
    when (localForecast) {
        WeatherType.Sun -> {
            SunAccompanist(modifier = modifier.offset(x = 64.dp, y = (-140).dp))
        }
        WeatherType.Rain -> {
            ParticleGenerator(
                modifier = Modifier.rotate(15f).offset(x = 40.dp, y = (-40).dp),
                width = parentWidth,
                height = parentHeight,
                denseFactor = 15,
                animationProperties = ParticleAnimationProperties(
                    minAnimationDurationMs = 1000,
                    maxAnimationDurationMs = 1200,
                    randomStartDelay = true,
                    minAlpha = .4f
                )
            ) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(6.dp)
                        .background(AppTheme.colors.primary)
                )
            }
            ParticleGenerator(
                width = parentWidth,
                height = parentHeight,
                isVertical = false,
                modifier = Modifier.padding(bottom = 420.dp, top = 120.dp),
                randomizeParticleSize = true,
                denseFactor = 22,
                animationProperties = ParticleAnimationProperties(
                    minAnimationDurationMs = 10000,
                    maxAnimationDurationMs = 40000,
                    randomStartDelay = true,
                    particleMinWidth = 45.dp,
                    particleMinHeight = 25.dp,
                    particleMaxWidth = 120.dp,
                    particleMaxHeight = 45.dp,
                    minAlpha = .4f,
                    randomSide = false
                )
            ) {
                Image(painter = painterResource(id = R.drawable.ic_single_cloud), contentDescription = null)
            }
        }
        WeatherType.Snow -> {
            ParticleGenerator(
                modifier = Modifier.rotate(-15f).offset(x = (-40).dp, y = (-40).dp),
                width = parentWidth,
                height = parentHeight,
                animationProperties = ParticleAnimationProperties(verticalXOffset = 32.dp, randomStartDelay = true)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_snow), contentDescription = null, tint = AppTheme.colors.primary)
            }
        }
        WeatherType.Cloud -> {
            ParticleGenerator(
                width = parentWidth,
                height = parentHeight,
                isVertical = false,
                modifier = Modifier.padding(bottom = 420.dp, top = 120.dp),
                randomizeParticleSize = true,
                denseFactor = 18,
                animationProperties = ParticleAnimationProperties(
                    minAnimationDurationMs = 20000,
                    maxAnimationDurationMs = 60000,
                    particleMinWidth = 45.dp,
                    particleMinHeight = 25.dp,
                    particleMaxWidth = 120.dp,
                    particleMaxHeight = 45.dp,
                    randomStartDelay = true,
                    minAlpha = .4f,
                    randomSide = false
                )
            ) {
                Image(painter = painterResource(id = R.drawable.ic_cloudy_cloud), contentDescription = null)
            }
        }
        else -> {}
    }
}

@Composable
fun ParticleGenerator(
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier,
    denseFactor: Int = 20,
    randomizeParticleSize: Boolean = true,
    isVertical: Boolean = true,
    animationProperties: ParticleAnimationProperties = ParticleAnimationProperties(),
    meshUnit: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()

    val generatorAmount = if (isVertical) {
        (width.value / denseFactor).toInt()
    } else {
        (height.value / denseFactor).toInt()
    }

    val randomParticleAlpha = remember {
        List(size = generatorAmount) {
            (animationProperties.maxAlpha - animationProperties.minAlpha) * Random.nextFloat() + animationProperties.minAlpha
        }
    }

    val randomDelayList = remember {
        List(size = generatorAmount) {
            Random.nextInt(600, 4000)
        }
    }

    val randomDurationList = remember {
        List(size = generatorAmount) {
            Random.nextInt(
                animationProperties.minAnimationDurationMs,
                animationProperties.maxAnimationDurationMs
            )
        }
    }

    val randomWidthList = remember {
        if (randomizeParticleSize) {
            List(size = generatorAmount) {
                Random.nextInt(
                    animationProperties.particleMinWidth.value.toInt(),
                    animationProperties.particleMaxWidth.value.toInt()
                )
            }
        } else {
            emptyList()
        }
    }

    val randomHeightList = remember {
        if (randomizeParticleSize) {
            List(size = generatorAmount) {
                Random.nextInt(
                    animationProperties.particleMinHeight.value.toInt(),
                    animationProperties.particleMaxHeight.value.toInt()
                )
            }
        } else {
            emptyList()
        }
    }

    if (isVertical) {
        Row(
            modifier = modifier
                .width(width = width)
                .height(height = height),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            for (i in 0 until generatorAmount) {

                val position by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = randomDurationList[i],
                            easing = LinearEasing,
                            delayMillis = if (animationProperties.randomStartDelay) randomDelayList[i] else 0
                        )
                    )
                )

                val xOffset by infiniteTransition.animateFloat(
                    initialValue = -1f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = randomDurationList[i] / animationProperties.counterOffsetAnimationSlowdown,
                            easing = LinearEasing
                        ),
                        repeatMode = RepeatMode.Reverse
                    )
                )

                Box(
                    modifier = Modifier
                        .offset(
                            y = height - (height * position),
                            x = animationProperties.verticalXOffset * xOffset
                        )
                        .alpha(randomParticleAlpha[i])
                        .width(if (randomizeParticleSize) randomWidthList[i].dp else animationProperties.particleWidth)
                        .height(if (randomizeParticleSize) randomHeightList[i].dp else animationProperties.particleHeight)
                ) {
                    meshUnit()
                }
            }
        }
    } else {
        Column(
            modifier = modifier
                .width(width = width)
                .height(height = height),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            for (i in 0 until generatorAmount) {

                val position by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 0f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = randomDurationList[i],
                            easing = LinearEasing,
                            delayMillis = if (animationProperties.randomStartDelay) randomDelayList[i] else 0
                        )
                    )
                )

                val yOffset by infiniteTransition.animateFloat(
                    initialValue = -1f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = randomDurationList[i] / animationProperties.counterOffsetAnimationSlowdown,
                            easing = LinearEasing
                        )
                    )
                )

                Box(
                    modifier = Modifier
                        .offset(
                            x = width - (width * position * 1.5f),
                            animationProperties.horizontalYOffset * yOffset
                        )
                        .alpha(randomParticleAlpha[i])
                        .width(if (randomizeParticleSize) randomWidthList[i].dp else animationProperties.particleWidth)
                        .height(if (randomizeParticleSize) randomHeightList[i].dp else animationProperties.particleHeight)
                ) {
                    meshUnit()
                }
            }
        }
    }
}

data class ParticleAnimationProperties(
    val particleMinWidth: Dp = 4.dp,
    val particleMaxWidth: Dp = 16.dp,
    val particleMinHeight: Dp = 4.dp,
    val particleMaxHeight: Dp = 16.dp,
    val particleWidth: Dp = particleMinWidth,
    val particleHeight: Dp = particleMinHeight,
    val randomSide: Boolean = false,
    val randomStartDelay: Boolean = false,
    val minAnimationDurationMs: Int = 2500,
    val maxAnimationDurationMs: Int = 6500,
    val verticalXOffset: Dp = 0.dp,
    val horizontalYOffset: Dp = 0.dp,
    val counterOffsetAnimationSlowdown: Int = 3,
    val minAlpha: Float = 1f,
    val maxAlpha: Float = 1f
)

@Composable
fun SunAccompanist(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()

    val innerCircle by infiniteTransition.animateFloat(
        initialValue = 74.dp.value,
        targetValue = 120.dp.value,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val outerCircle by infiniteTransition.animateFloat(
        initialValue = 74.dp.value,
        targetValue = 180.dp.value,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    Image(
        modifier = modifier
            .size(outerCircle.dp)
            .alpha(.35f),
        painter = painterResource(id = R.drawable.ic_sun_accomp), contentDescription = null
    )
    Image(
        modifier = modifier
            .size(innerCircle.dp)
            .alpha(.55f),
        painter = painterResource(id = R.drawable.ic_sun_accomp), contentDescription = null
    )
    Image(modifier = modifier.size(74.dp), painter = painterResource(id = R.drawable.ic_sun_accomp), contentDescription = null)
}
