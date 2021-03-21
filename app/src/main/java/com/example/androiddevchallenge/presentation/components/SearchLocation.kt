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

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.domain.entity.location.Location
import com.example.androiddevchallenge.presentation.theme.AppTheme

enum class SearchLocationState { Expanded, Collapsed }

@Composable
fun SearchLocation(
    onQueryChanged: (String) -> Unit,
    locations: List<Location>?,
    modifier: Modifier = Modifier,
    query: String? = "",
    onCityAdded: (Location) -> Unit
) {
    var state by remember { mutableStateOf(SearchLocationState.Collapsed) }
    val focusRequester = remember { FocusRequester() }

    val shape = if (state == SearchLocationState.Expanded) RoundedCornerShape(
        topStart = 16.dp,
        topEnd = 16.dp
    ) else CircleShape

    Surface(modifier = modifier, shape = shape, elevation = 2.dp) {
        Box(
            Modifier
                .background(AppTheme.colors.surface)
                .animateContentSize()
        ) {
            when (state) {
                SearchLocationState.Expanded -> {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            BasicTextField(
                                modifier = Modifier
                                    .weight(2f)
                                    .focusRequester(focusRequester),
                                value = query ?: "",
                                onValueChange = { onQueryChanged(it) },
                                textStyle = AppTheme.typography.button.copy(AppTheme.colors.onBackground)
                            )
                            DisposableEffect("") {
                                focusRequester.requestFocus()
                                onDispose {}
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Icon(
                                modifier = Modifier.clickable { state = SearchLocationState.Collapsed },
                                painter = painterResource(id = R.drawable.ic_cross),
                                contentDescription = stringResource(id = R.string.cd_icon_close_search)
                            )
                        }
                        Divider(
                            color = AppTheme.colors.secondary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .width(1.dp)
                        )
                        locations?.let {
                            if (it.isEmpty()) {
                                Text(
                                    modifier = Modifier
                                        .padding(vertical = 4.dp)
                                        .align(Alignment.CenterHorizontally),
                                    text = stringResource(R.string.query_hint),
                                    style = AppTheme.typography.button.copy(color = AppTheme.colors.onSurface)
                                )
                            } else {
                                LazyColumn(
                                    Modifier.heightIn(max = 400.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(locations) { location ->
                                        Text(
                                            modifier = Modifier
                                                .clickable {
                                                    onCityAdded(location)
                                                    state = SearchLocationState.Collapsed
                                                }
                                                .fillMaxWidth()
                                                .padding(vertical = 8.dp, horizontal = 8.dp),
                                            text = "${location.name}, ${location.country}",
                                            style = AppTheme.typography.button.copy(color = AppTheme.colors.onBackground)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                SearchLocationState.Collapsed -> {
                    Icon(
                        modifier = Modifier
                            .clickable { state = SearchLocationState.Expanded }
                            .padding(8.dp),
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = stringResource(R.string.cd_add_location)
                    )
                }
            }
        }
    }
}
