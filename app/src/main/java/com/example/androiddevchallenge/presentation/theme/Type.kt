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
package com.example.androiddevchallenge.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.R

private val LatoRegular = Font(
    R.font.lato
)

private val LatoFontFamily = FontFamily(
    LatoRegular
)

val typography = Typography(
    h3 = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 80.sp,
        lineHeight = 90.sp,
        fontFeatureSettings = """'pnum' on, 'lnum' on;"""
    ),
    h2 = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp,
        lineHeight = 24.sp,
    ),
    button = TextStyle(
        fontFamily = LatoFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 15.sp,
        lineHeight = 18.sp
    )
)
