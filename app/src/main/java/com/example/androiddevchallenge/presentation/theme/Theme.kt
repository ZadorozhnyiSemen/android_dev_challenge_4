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
package com.example.androiddevchallenge.presentation.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.example.androiddevchallenge.domain.entity.weather.WeatherType
import com.example.androiddevchallenge.presentation.theme.system.LocalSysUiController
import com.example.androiddevchallenge.ui.theme.typography

private val sunColorPalette = CustomColors(
    background = white,
    bg_gradient = listOf(white, yellow200),
    surface = white,
    primary = yellow600,
    secondary = yellow400,
    onBackground = gray800,
    onSurface = gray500
)

private val cloudyColorPalette = CustomColors(
    background = gray200,
    bg_gradient = listOf(gray200, gray300),
    surface = white,
    primary = gray600,
    secondary = gray400,
    onBackground = gray800,
    onSurface = gray500
)

private val rainColorPalette = CustomColors(
    background = blue200,
    bg_gradient = listOf(blue200, blue300),
    surface = white,
    primary = blue600,
    secondary = blue400,
    onBackground = gray800,
    onSurface = gray500
)

private val snowColorPalette = CustomColors(
    background = cyan200,
    bg_gradient = listOf(cyan200, cyan300),
    surface = white,
    primary = cyan600,
    secondary = cyan400,
    onBackground = gray800,
    onSurface = gray500

)

private val defaultColorPalette = CustomColors(
    background = gray200,
    bg_gradient = listOf(gray200, gray300),
    surface = white,
    primary = gray600,
    secondary = gray400,
    onBackground = gray800,
    onSurface = gray500

)

@Composable
fun WeatherTheme(
    type: WeatherType?,
    content: @Composable() () -> Unit
) {

    val colors = when (type) {
        WeatherType.Sun -> sunColorPalette
        WeatherType.Cloud -> cloudyColorPalette
        WeatherType.Rain -> rainColorPalette
        WeatherType.Snow -> snowColorPalette
        else -> defaultColorPalette
    }

    val sys = LocalSysUiController.current
    SideEffect {
        sys.setSystemBarsColor(
            color = colors.background
        )
    }
    ProvideCustomColors(colors = colors) {
        MaterialTheme(
            typography = typography,
            content = content
        )
    }
}

object AppTheme {
    val colors: CustomColors
        @Composable
        get() = LocalCustomColors.current

    val typography: Typography
        @Composable
        get() = MaterialTheme.typography
}

@Stable
class CustomColors(
    background: Color,
    bg_gradient: List<Color>,
    surface: Color,
    primary: Color,
    secondary: Color,
    onBackground: Color,
    onSurface: Color
) {
    var background by mutableStateOf(background)
        private set
    var bg_gradient by mutableStateOf(bg_gradient)
        private set
    var surface by mutableStateOf(surface)
        private set
    var primary by mutableStateOf(primary)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var onBackground by mutableStateOf(onBackground)
        private set
    var onSurface by mutableStateOf(onSurface)
        private set

    fun update(other: CustomColors) {
        background = other.background
        bg_gradient = other.bg_gradient
        surface = other.surface
        primary = other.primary
        secondary = other.secondary
        onBackground = other.onBackground
        onSurface = other.onSurface
    }
}

@Composable
fun ProvideCustomColors(
    colors: CustomColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalCustomColors provides colorPalette, content = content)
}

private val LocalCustomColors = staticCompositionLocalOf<CustomColors> {
    error("ColorPalette not provided")
}
