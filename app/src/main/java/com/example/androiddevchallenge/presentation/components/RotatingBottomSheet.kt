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

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.theme.AppTheme

sealed class RotatingBottomSheetState {
    data class Collapsed(val from: RotatingBottomSheetState? = null) : RotatingBottomSheetState()
    object LeftDragging : RotatingBottomSheetState()
    object LeftExpanded : RotatingBottomSheetState()
    object RightExpanded : RotatingBottomSheetState()
    object RightDragging : RotatingBottomSheetState()
}

@Composable
fun RotatingBottomSheet(
    leftIcon: @Composable () -> Unit,
    leftContent: @Composable () -> Unit,
    rightIcon: @Composable () -> Unit,
    rightContent: @Composable () -> Unit,
    closeIcon: @Composable () -> Unit,
    title: @Composable () -> Unit,
    boxSize: Dp,
    modifier: Modifier = Modifier
) {
    var state: RotatingBottomSheetState by remember { mutableStateOf(RotatingBottomSheetState.Collapsed()) }
    val sheetTransition = updateTransition(targetState = state)

    val leftOffset by sheetTransition.animateDp(
        transitionSpec = {
            tween(300, easing = FastOutSlowInEasing)
        }
    ) {
        when (it) {
            RotatingBottomSheetState.LeftExpanded -> 0.dp
            else -> boxSize - 120.dp
        }
    }

    val rightOffset by sheetTransition.animateDp(
        transitionSpec = {
            tween(300, easing = FastOutSlowInEasing)
        }
    ) {
        when (it) {
            RotatingBottomSheetState.RightExpanded -> 0.dp
            else -> boxSize - 120.dp
        }
    }

    val leftRotation by sheetTransition.animateFloat(
        transitionSpec = {
            tween(300, easing = FastOutSlowInEasing)
        }
    ) {
        when (it) {
            RotatingBottomSheetState.LeftExpanded -> 0f
            else -> -90f
        }
    }

    val rightRotation by sheetTransition.animateFloat(
        transitionSpec = {
            tween(300, easing = FastOutSlowInEasing)
        }
    ) {
        when (it) {
            RotatingBottomSheetState.RightExpanded -> 0f
            else -> 90f
        }
    }

    val openVisibility by sheetTransition.animateFloat(
        transitionSpec = {
            snap(delayMillis = 0)
        }
    ) {
        when (it) {
            RotatingBottomSheetState.LeftExpanded, RotatingBottomSheetState.RightExpanded -> 1f
            else -> 0f
        }
    }

    val closeVisibility by sheetTransition.animateFloat(
        transitionSpec = {
            snap(delayMillis = 300)
        }
    ) {
        when (it) {
            is RotatingBottomSheetState.Collapsed -> 1f
            else -> 0f
        }
    }

    val cd = stringResource(id = R.string.cd_weather_details)

    Box(modifier = modifier) {
        if (state is RotatingBottomSheetState.Collapsed && closeVisibility == 1f) {
            Row(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 64.dp, topEnd = 64.dp))
                    .background(AppTheme.colors.surface)
                    .semantics { contentDescription = cd }
                    .semantics { heading() }
                    .padding(32.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            state = RotatingBottomSheetState.LeftExpanded
                        }
                ) { leftIcon() }
                title()
                Box(
                    modifier = Modifier.clickable {
                        state = RotatingBottomSheetState.RightExpanded
                    }
                ) { rightIcon() }
            }
        }

        if (state is RotatingBottomSheetState.Collapsed &&
            (state as RotatingBottomSheetState.Collapsed).from == RotatingBottomSheetState.RightExpanded &&
            closeVisibility != 1f ||
            state == RotatingBottomSheetState.RightExpanded && openVisibility == 1f
        ) {
            // left
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = rightOffset)
                    .rotate(rightRotation)
                    .clip(RoundedCornerShape(64.dp))
                    .background(AppTheme.colors.surface)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .padding(32.dp)
                        .clip(CircleShape)
                        .clickable {
                            state =
                                RotatingBottomSheetState.Collapsed(RotatingBottomSheetState.RightExpanded)
                        }
                        .align(Alignment.TopStart)
                ) {
                    closeIcon()
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(start = 120.dp)
                ) {
                    rightContent()
                }
                Divider(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 120.dp)
                        .width(1.dp)
                        .fillMaxHeight(),
                    color = AppTheme.colors.secondary
                )
            }
        }

        if (state is RotatingBottomSheetState.Collapsed &&
            (state as RotatingBottomSheetState.Collapsed).from == RotatingBottomSheetState.LeftExpanded &&
            closeVisibility != 1f ||
            state == RotatingBottomSheetState.LeftExpanded && openVisibility == 1f
        ) {
            // left
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = leftOffset)
                    .rotate(leftRotation)
                    .clip(RoundedCornerShape(64.dp))
                    .background(AppTheme.colors.surface)
            ) {
                Box(
                    modifier = Modifier
                        .padding(32.dp)
                        .clip(CircleShape)
                        .clickable {
                            state =
                                RotatingBottomSheetState.Collapsed(RotatingBottomSheetState.LeftExpanded)
                        }
                        .align(Alignment.TopEnd)
                ) {
                    closeIcon()
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(end = 120.dp)
                ) {
                    leftContent()
                }
                Divider(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = 120.dp)
                        .width(1.dp)
                        .fillMaxHeight(),
                    color = AppTheme.colors.secondary
                )
            }
        }
    }
}
