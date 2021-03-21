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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.domain.entity.location.Location
import com.example.androiddevchallenge.presentation.theme.AppTheme

@Composable
fun LocationList(
    locations: List<Location>?,
    selected: Location?,
    onClick: (Location) -> Unit,
    modifier: Modifier = Modifier
) {
    locations?.let {
        LazyRow(
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(locations) { location ->
                LocationItem(
                    location = location,
                    selected = location == selected,
                    modifier = Modifier
                        .clickable {
                            onClick(location)
                        }
                        .background(AppTheme.colors.surface)
                        .padding(vertical = 12.dp, horizontal = 24.dp)
                )
            }
        }
    }
}

@Composable
fun LocationItem(
    location: Location,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    val border = if (selected) BorderStroke(1.dp, AppTheme.colors.primary) else null
    val color = if (selected) AppTheme.colors.onBackground else AppTheme.colors.onSurface
    Surface(
        shape = CircleShape,
        color = AppTheme.colors.surface,
        elevation = 2.dp,
        border = border
    ) {
        Text(modifier = modifier, text = location.name, style = AppTheme.typography.button.copy(color = color))
    }
}
