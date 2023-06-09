package com.interview.weatherapi_codingchallenge.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interview.weatherapi_codingchallenge.data.model.geocoding.GeocodingItemModel
import com.interview.weatherapi_codingchallenge.data.model.weather.WeatherModel
import com.interview.weatherapi_codingchallenge.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CurrentLocationViewModel @Inject constructor(
    val repository: Repository
) :ViewModel(){

    private val _weather = MutableStateFlow(WeatherModel())
    val weather: StateFlow<WeatherModel> = _weather


    private val _search = MutableStateFlow<List<GeocodingItemModel>>(emptyList())
    val search: StateFlow<List<GeocodingItemModel>> = _search

    fun getWeather(latitude: Double, longitude: Double, apiKey: String){
        viewModelScope.launch(Dispatchers.IO){
            withContext(NonCancellable){
                val response = repository.getWeather(latitude, longitude, apiKey)
                _weather.value = response
            }
        }
    }

    fun getLocation(query: String, apiKey: String){
        viewModelScope.launch(Dispatchers.IO){
            withContext(NonCancellable){
                val response = repository.getCoordinates(query, 5, apiKey)
                _search.value = response
            }
        }
    }

}