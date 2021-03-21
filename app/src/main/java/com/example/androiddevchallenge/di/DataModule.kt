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
package com.example.androiddevchallenge.di

import android.content.Context
import com.example.androiddevchallenge.data.entity.MyObjectBox
import com.example.androiddevchallenge.data.mappers.LocationMapper
import com.example.androiddevchallenge.data.mappers.WeatherMapper
import com.example.androiddevchallenge.data.repository.LocationRepositoryImpl
import com.example.androiddevchallenge.data.repository.UserPreferencesRepositoryImpl
import com.example.androiddevchallenge.data.repository.WeatherRepositoryImpl
import com.example.androiddevchallenge.data.source.location.LocationApi
import com.example.androiddevchallenge.data.source.location.LocationCache
import com.example.androiddevchallenge.data.source.location.LocationNetworkSource
import com.example.androiddevchallenge.data.source.user.PredefinedPreferences
import com.example.androiddevchallenge.data.source.user.PreferencesCache
import com.example.androiddevchallenge.data.source.user.PreferencesDb
import com.example.androiddevchallenge.data.source.weather.WeatherApi
import com.example.androiddevchallenge.data.source.weather.WeatherCache
import com.example.androiddevchallenge.data.source.weather.WeatherNetworkSource
import com.example.androiddevchallenge.domain.bridge.LocationRepository
import com.example.androiddevchallenge.domain.bridge.UserPreferencesRepository
import com.example.androiddevchallenge.domain.bridge.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.objectbox.BoxStore
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherNetworkSource: WeatherNetworkSource,
        weatherCache: WeatherCache,
        locationMapper: LocationMapper,
        weatherMapper: WeatherMapper
    ): WeatherRepository = WeatherRepositoryImpl(
        weatherNetworkSource,
        weatherCache,
        locationMapper,
        weatherMapper,
        Dispatchers.IO
    )

    @Provides
    @Singleton
    fun provideLocationRepository(
        locationApi: LocationNetworkSource,
        locationCache: LocationCache,
        locationMapper: LocationMapper
    ): LocationRepository = LocationRepositoryImpl(
        locationApi,
        locationCache,
        locationMapper,
        Dispatchers.IO
    )

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(
        preferencesCache: PreferencesCache,
        preferencesDb: PreferencesDb,
        defaultPreferences: PredefinedPreferences,
        locationMapper: LocationMapper
    ): UserPreferencesRepository = UserPreferencesRepositoryImpl(
        preferencesCache,
        preferencesDb,
        defaultPreferences,
        locationMapper,
        Dispatchers.IO
    )

    @Provides
    @Singleton
    fun provideBoxStore(@ApplicationContext context: Context): BoxStore? =
        MyObjectBox.builder()
            .androidContext(context)
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.HEADERS) })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.troposphere.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun provideLocationApi(retrofit: Retrofit): LocationApi = retrofit.create(LocationApi::class.java)
}
