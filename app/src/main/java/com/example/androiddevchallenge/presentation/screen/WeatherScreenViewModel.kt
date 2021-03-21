package com.example.androiddevchallenge.presentation.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.domain.entity.location.Location
import com.example.androiddevchallenge.domain.entity.weather.Weather
import com.example.androiddevchallenge.domain.usecase.location.GetCitiesUseCase
import com.example.androiddevchallenge.domain.usecase.location.GetFavoriteLocationsUseCase
import com.example.androiddevchallenge.domain.usecase.location.SaveLocationToFavoritesUseCase
import com.example.androiddevchallenge.domain.usecase.weather.GetForecastInfoForLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherScreenViewModel @Inject constructor(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getForecastInfoForLocationUseCase: GetForecastInfoForLocationUseCase,
    private val saveLocationToFavoritesUseCase: SaveLocationToFavoritesUseCase,
    private val getFavoritesUseCase: GetFavoriteLocationsUseCase
) : ViewModel() {

    private val _selectedItem = MutableLiveData<Weather>()
    private val _selectedLocation = MutableLiveData<Location>()
    private val _searchResult = MutableLiveData<List<Location>>(emptyList())
    private val _searchQuery = MutableLiveData("")
    private val _favoriteLocations = MutableLiveData<List<Location>>(emptyList())
    val selectedItem: LiveData<Weather> = _selectedItem
    val selectedLocation: LiveData<Location> = _selectedLocation
    val searchResult: LiveData<List<Location>> = _searchResult
    val searchQuery: LiveData<String> = _searchQuery
    val favoriteLocations: LiveData<List<Location>> = _favoriteLocations

    init {
        viewModelScope.launch {
            getFavoritesUseCase().collect {
                _favoriteLocations.value = it
                val first = it.first()
                onCitySelected(first)
            }
        }
    }

    fun onCitySelected(location: Location) {
        println("Selected location $location")
        viewModelScope.launch {
            getForecastInfoForLocationUseCase.invoke(location).collect {
                it.location.selected = true
                _selectedItem.value = it
                _selectedLocation.value = location
            }
        }
    }

    fun onCityAdded(location: Location) {
        viewModelScope.launch {
            saveLocationToFavoritesUseCase.invoke(location)
            getFavoritesUseCase().collect {
                _favoriteLocations.value = it
            }
        }
    }

    fun onQueryChanged(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            getCitiesUseCase(query).collect {
                _searchResult.value = it
            }
        }
    }
}